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

@WebServlet("/userProfile")
public class UserProfile extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (Integer) session.getAttribute("userId");
        Account account = accountService.getUserById(userId);
        List<Phone> phones = account.getPhones();
        List<Phone> homePhones = new ArrayList<>();
        List<Phone> workPhones = new ArrayList<>();
        for (Phone phone : phones) {
            if (phone.getType() == HOME) {
                homePhones.add(phone);
            } else {
                workPhones.add(phone);
            }
        }
        req.setAttribute("account", account);
        req.setAttribute("homePhones", homePhones);
        req.setAttribute("workPhones", workPhones);
        req.getRequestDispatcher("/jsp/userprofile.jsp").forward(req, resp);
    }
}
