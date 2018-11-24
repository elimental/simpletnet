package com.getjavajob.simplenet.web.util;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.PhoneType.HOME;


public class WebUtils {

    public static void preparePhonesForModel(List<Phone> phones, List<Phone> homePhones, List<Phone> workPhones) {
        if (phones != null) {
            for (Phone phone : phones) {
                if (phone.getType() == HOME) {
                    homePhones.add(phone);
                } else {
                    workPhones.add(phone);
                }
            }
        }
    }

    public static List<Phone> removeNullNumbers(List<Phone> phones) {
        List<Phone> result = new ArrayList<>();
        for (Phone phone : phones) {
            if (phone.getNumber() != null) {
                result.add(phone);
            }
        }
        return result;
    }

    public static void setPhoneOwner(List<Phone> phones, Account account) {
        for (Phone phone : phones) {
            phone.setPhoneOwner(account);
        }
    }

    public static void deleteCookie(HttpServletResponse response, String... cookies) {
        for (String cookieName : cookies) {
            Cookie cookie = new Cookie(cookieName, "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    public static void validateXML(String xsdFile, InputStream xmlInputStream) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source xsd = new StreamSource(WebUtils.class.getClassLoader().getResourceAsStream(xsdFile));
        Schema schema = factory.newSchema(xsd);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xmlInputStream));
    }
}
