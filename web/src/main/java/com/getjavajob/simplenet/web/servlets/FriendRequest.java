package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/friendRequest")
public class FriendRequest extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fromUserId = (Integer) req.getSession().getAttribute("userId");
        int toUserId = Integer.parseInt(req.getParameter("id"));
        accountService.sendFriendRequest(fromUserId, toUserId);
        resp.sendRedirect("/friends");
    }
}
