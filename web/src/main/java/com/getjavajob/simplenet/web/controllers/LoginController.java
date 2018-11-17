package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.getjavajob.simplenet.web.util.WebUtils.deleteCookie;

@Controller
public class LoginController {

    private final AccountService accountService;

    @Autowired
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/", "/login"})
    public String showLoginPage(HttpSession session, HttpServletRequest request) {
        Long userIdInSession = (Long) session.getAttribute("userId");
        if (userIdInSession != null) {
            return "redirect:/userProfile?id=" + userIdInSession;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "login/login";
        }
        Map<String, String> cookiesMap = new HashMap<>();
        for (Cookie cookie : cookies) {
            cookiesMap.put(cookie.getName(), cookie.getValue());
        }
        String emailCookie = cookiesMap.get("email");
        String passwordCookie = cookiesMap.get("password");
        if (emailCookie != null && passwordCookie != null) {
            if (accountService.checkLogin(emailCookie, passwordCookie)) {
                return loginProcessRedirect(emailCookie, session);
            } else {
                return "login/loginError";
            }
        } else {
            return "login/login";
        }
    }

    @PostMapping("/checkLogin")
    public String checkLogin(String email, String password, boolean rememberMe,
                             HttpServletResponse response, HttpSession session) {
        if (accountService.checkLogin(email, password)) {
            if (rememberMe) {
                response.addCookie(new Cookie("email", email));
                response.addCookie(new Cookie("password", password));
            }
            return loginProcessRedirect(email, session);
        } else {
            return "login/loginError";
        }
    }

    private String loginProcessRedirect(String email, HttpSession session) {
        Account account = accountService.getAccountByEmail(email);
        Long userId = account.getId();
        session.setAttribute("userId", account.getId());
        session.setAttribute("userName", account.getFirstName());
        return "redirect:/userProfile?id=" + userId;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        deleteCookie(response, "email", "pasword");
        session.invalidate();
        return "redirect:/login";
    }
}
