package com.getjavajob.simplenet.web.servlets.login;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginCheck")
public class LoginCheck extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        if (email == null && password == null) {
            Cookie[] cookies = req.getCookies();
            Map<String, String> cookiesMap = new HashMap<>();
            for (Cookie cookie : cookies) {
                cookiesMap.put(cookie.getName(), cookie.getValue());
            }
            email = cookiesMap.get("email");
            password = cookiesMap.get("password");
        }
        if (accountService.checkLogin(email, password)) {
            Account account = accountService.getUserByEmail(email);
            int userId = account.getId();
            String userName = account.getFirstName();
            HttpSession session = req.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("userName", userName);
            if (remember != null) {
                Cookie emailCookie = new Cookie("email", email);
                Cookie passCookie = new Cookie("password", password);
                resp.addCookie(emailCookie);
                resp.addCookie(passCookie);
            }
            resp.sendRedirect("/userProfile?id=" + userId);
        } else {
            resp.sendRedirect("/loginError");
        }
    }
}
