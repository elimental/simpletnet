package com.getjavajob.simplenet.web.servlets.userprofile;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.web.util.ServletHelper.preparePhones;

@WebServlet("/editProfile")
public class EditProfile extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Account account = accountService.getUserById(id);
        List<Phone> phones = account.getPhones();
        List<Phone> homePhones = new ArrayList<>();
        List<Phone> workPhones = new ArrayList<>();
        preparePhones(phones, homePhones, workPhones);
        req.setAttribute("account", account);
        req.setAttribute("homePhones", homePhones);
        req.setAttribute("workPhones", workPhones);
        req.getRequestDispatcher("/jsp/userprofile/editprofile.jsp").forward(req, resp);
    }
}
