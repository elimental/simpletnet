package com.getjavajob.simplenet.web.security;

import com.getjavajob.simplenet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        Long userIdInSession = (Long) session.getAttribute("userId");
        if (userIdInSession == null) {
            userIdInSession = accountService.getAccountByEmail(authentication.getName()).getId();
            session.setAttribute("userId", userIdInSession);
        }
        httpServletResponse.sendRedirect("/userProfile?id=" + userIdInSession);
    }
}
