package com.getjavajob.simplenet.web.servlets.friends;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/friends")
public class ShowFriends extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = (Integer) req.getSession().getAttribute("userId");
        List<Account> friends = accountService.getFriends(id);
        req.setAttribute("friends", friends);
        List<Account> requestedFriends = accountService.getRequestedFriends(id);
        req.setAttribute("requestedFriends", requestedFriends);
        List<Account> requestFromFriends = accountService.getRequestFromFriends(id);
        req.setAttribute("requestFromFriends", requestFromFriends);
        req.getRequestDispatcher("/jsp/friends/friends.jsp").forward(req, resp);
    }
}
