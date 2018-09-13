package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.PhoneType.HOME;
import static com.getjavajob.simplenet.web.util.ServletHelper.preparePhones;

@WebServlet("/editProfile")
public class EditProfile extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (Integer) session.getAttribute("userId");
        Account account = accountService.getUserById(userId);
        List<Phone> phones = account.getPhones();
        List<Phone> homePhones = new ArrayList<>();
        List<Phone> workPhones = new ArrayList<>();
        preparePhones(phones,homePhones,workPhones);
        req.setAttribute("account", account);
        req.setAttribute("homePhones", homePhones);
        req.setAttribute("workPhones", workPhones);
        req.getRequestDispatcher("/jsp/editprofile.jsp").forward(req, resp);
    }
}
