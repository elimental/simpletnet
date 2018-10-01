package com.getjavajob.simplenet.web.servlets.userprofile;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profileAccessDenied")
public class ProfileAccessDenied extends HttpServlet {
    private AccountService accountService = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int requestedUserId = Integer.parseInt(req.getParameter("id"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        req.setAttribute("requestedUserId", requestedUserId);
        boolean alreadyRequested = accountService.ifAlreadyRequested(userId, requestedUserId);
        req.setAttribute("alreadyRequested", alreadyRequested);
        req.getRequestDispatcher("/jsp/userprofile/profileaccessdenied.jsp").forward(req, resp);
    }
}
