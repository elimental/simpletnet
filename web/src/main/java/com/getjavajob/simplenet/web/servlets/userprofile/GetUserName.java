package com.getjavajob.simplenet.web.servlets.userprofile;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getUserName")
public class GetUserName extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        Account account = accountService.getUserById(userId);
        StringBuilder userName = new StringBuilder();
        userName.append(account.getFirstName()).append(" ").append(account.getLastName());
        resp.getWriter().append(userName.toString());
    }
}
