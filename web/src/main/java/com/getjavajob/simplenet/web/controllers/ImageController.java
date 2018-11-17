package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.CommunityService;
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

    private final AccountService accountService;
    private final CommunityService communityService;

    @Autowired
    public ImageController(AccountService accountService, CommunityService communityService) {
        this.accountService = accountService;
        this.communityService = communityService;
    }

    @GetMapping("/getImage")
    public @ResponseBody
    byte[] getImage(String type, long id, HttpServletRequest request) throws IOException {
        byte[] img = null;
        String defaultPath = null;
        if (type.equals("account")) {
            img = accountService.getImage(id);
            defaultPath = ACCOUNT_DEFAULT_IMAGE_PATH;
        } else if (type.equals("community")) {
            img = communityService.getImage(id);
            defaultPath = GROUP_DEFAULT_IMAGE_PATH;
        }
        return img == null ? IOUtils.toByteArray(request.getServletContext().getResourceAsStream(defaultPath)) : img;
    }
}
