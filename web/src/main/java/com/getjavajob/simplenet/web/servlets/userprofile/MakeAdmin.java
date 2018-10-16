package com.getjavajob.simplenet.web.servlets.userprofile;

import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.simplenet.common.entity.Role.ADMINISTRATOR;

@WebServlet("/makeAdmin")
public class MakeAdmin extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int adminId = (Integer) req.getSession().getAttribute("userId");
        boolean admin = accountService.ifAdmin(adminId);
        if (!admin) {
            resp.sendRedirect("/accessDenied");
        }
        int id = Integer.parseInt(req.getParameter("id"));
        accountService.updateUserRole(id, ADMINISTRATOR);
        resp.sendRedirect("/userProfile?id=" + id);
    }
}
