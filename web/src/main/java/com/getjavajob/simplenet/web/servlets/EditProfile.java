package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.PicturesDAO;
import com.getjavajob.simplenet.dao.RelationshipDAO;
import com.getjavajob.simplenet.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editProfile")
public class EditProfile extends HttpServlet {
    private AccountService accountService = new AccountService(new AccountDAO(), new PhoneDAO(), new RelationshipDAO(),
            new PicturesDAO());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (Integer) session.getAttribute("userId");
        Account account = accountService.getUserById(userId);
        req.setAttribute("account", account);
        req.getRequestDispatcher("/jsp/editprofile.jsp").forward(req, resp);
    }
}
