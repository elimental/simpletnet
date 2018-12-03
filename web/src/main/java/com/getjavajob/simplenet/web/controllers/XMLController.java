package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.service.AccountService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.*;
import com.thoughtworks.xstream.hibernate.mapper.HibernateMapper;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.getjavajob.simplenet.web.util.WebUtils.*;

@Controller
@SessionAttributes("userId")
public class XMLController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private static final String ACCOUNT_XML_XSD = "account_xml.xsd";

    private final AccountService accountService;
    private final XStream xStream;

    @Autowired
    public XMLController(AccountService accountService) {
        this.accountService = accountService;
        XStream xStream = new XStream(new DomDriver()) {
            protected MapperWrapper wrapMapper(final MapperWrapper next) {
                return new HibernateMapper(next);
            }
        };
        xStream.registerConverter(new HibernateProxyConverter());
        xStream.registerConverter(new HibernatePersistentCollectionConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentMapConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedMapConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedSetConverter(xStream.getMapper()));
        xStream.addDefaultImplementation(java.sql.Date.class, Date.class);
        xStream.processAnnotations(Account.class);
        xStream.processAnnotations(Phone.class);
        this.xStream = xStream;
    }

    @GetMapping("/exportAccountXML")
    public void getAccountXML(@SessionAttribute("userId") long userIdInSession, long id, HttpServletResponse response)
            throws IOException {
        logger.trace("User(id={}) is exporting account to xml", userIdInSession, id);
        Account account = accountService.getAccountById(id);
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition",
                "attachment; filename=account.xml");
        xStream.toXML(account, response.getOutputStream());
    }

    @PostMapping("/importAccountXML")
    public ModelAndView loadAccountXML(@SessionAttribute("userId") long userIdInSession,
                                       @RequestParam("xmlFile") MultipartFile xmlFile) throws IOException {
        logger.trace("User(id={}) is importing account from xml", userIdInSession);
        ModelAndView modelAndView = new ModelAndView("userprofile/editUserProfile");
        try {
            validateXML(ACCOUNT_XML_XSD, new ByteArrayInputStream(xmlFile.getBytes()));
        } catch (SAXException | IOException e) {
            logger.error("User(id={}) invalid xml file");
            return new ModelAndView("userprofile/invalidXML");
        }
        Account account = (Account) xStream.fromXML(new ByteArrayInputStream(xmlFile.getBytes()));
        account.setId(userIdInSession);
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            setPhoneOwner(phones, account);
            List<Phone> homePhones = new ArrayList<>();
            List<Phone> workPhones = new ArrayList<>();
            preparePhonesForModel(phones, homePhones, workPhones);
            modelAndView.addObject("homePhones", homePhones);
            modelAndView.addObject("workPhones", workPhones);
        }
        modelAndView.addObject("owner", true);
        modelAndView.addObject("account", account);
        return modelAndView;
    }
}
