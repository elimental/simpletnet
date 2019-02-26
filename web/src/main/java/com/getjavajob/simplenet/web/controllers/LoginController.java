package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final AccountService accountService;

    @Autowired
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping({"/", "/login"})
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                Model model, Authentication authentication, HttpSession session) {
        if (authentication != null) {
            Long userIdInSession = (Long) session.getAttribute("userId");
            if (userIdInSession == null) {
                Account account = accountService.getAccountByEmail(authentication.getName());
                userIdInSession = account.getId();
                session.setAttribute("userId", userIdInSession);
                session.setAttribute("userName", account.getFirstName());
            }
            return "redirect:/userProfile?id=" + userIdInSession;
        }
        model.addAttribute("error", error != null);
        return "login/login";
    }
}
