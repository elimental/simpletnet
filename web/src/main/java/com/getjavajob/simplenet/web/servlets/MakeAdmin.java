package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.simplenet.common.entity.Role.ADMINISTRATOR;

@WebServlet("/makeAdmin")
public class MakeAdmin extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int adminId = (Integer) req.getSession().getAttribute("userId");
        if (!accountService.ifAdmin(adminId)) {
            resp.sendRedirect("/accessDenied");
        }
        int id = Integer.parseInt(req.getParameter("id"));
        accountService.updateUserRole(id, ADMINISTRATOR);
        resp.sendRedirect("/userProfile?id=" + id);
    }
}
