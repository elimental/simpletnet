package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ImageController {

    private static final String ACCOUNT_DEFAULT_IMAGE_PATH = "/WEB-INF/images/account_default.png";
    private static final String GROUP_DEFAULT_IMAGE_PATH = "/WEB-INF/images/group_default.png";

    @Autowired
    AccountService accountService;
    @Autowired
    GroupService groupService;

    @GetMapping("/getImage")
    public @ResponseBody
    byte[] getImage(String type, int id, HttpServletRequest request) throws IOException {
        byte[] img = null;
        String defaultPath = null;
        if (type.equals("account")) {
            Account account = accountService.getUserById(id);
            img = account.getPhoto();
            defaultPath = ACCOUNT_DEFAULT_IMAGE_PATH;
        } else if (type.equals("group")) {
            Group group = groupService.getGroupById(id);
            img = group.getPicture();
            defaultPath = GROUP_DEFAULT_IMAGE_PATH;
        }
        return img == null ? IOUtils.toByteArray(request.getServletContext().getResourceAsStream(defaultPath)) : img;
    }
}
