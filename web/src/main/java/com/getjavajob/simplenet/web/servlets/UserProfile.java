package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.web.util.ServletHelper.*;

@WebServlet("/userProfile")
public class UserProfile extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int idInSession = (Integer) req.getSession().getAttribute("userId");
        Account account = accountService.getUserById(id);
        if (account == null) {
            deleteCookies(resp);
            invalidateSession(req);
            resp.sendRedirect("/login");
        } else {
            boolean owner = id == idInSession;
            boolean ifAdminOpens = accountService.ifAdmin(idInSession);
            boolean ifFriend = accountService.ifFriend(id, idInSession);
            if (!owner && !ifAdminOpens && !ifFriend) {
                req.getRequestDispatcher("/profileAccessDenied?id=" + id).forward(req, resp);
            } else {
                boolean editAndDelete = ifAdminOpens || owner;
                req.setAttribute("editAndDelete", editAndDelete);
                boolean ifAdminsProfile = accountService.ifAdmin(id);
                req.setAttribute("admin", ifAdminsProfile);
                boolean allowMakeAdmin = ifAdminOpens && !owner && !ifAdminsProfile;
                req.setAttribute("allowMakeAdmin", allowMakeAdmin);
                boolean showAddFriendButton = ifAdminOpens && !ifFriend && !owner;
                req.setAttribute("showAddFriendButton", showAddFriendButton);
                req.setAttribute("account", account);
                List<Phone> phones = account.getPhones();
                List<Phone> homePhones = new ArrayList<>();
                List<Phone> workPhones = new ArrayList<>();
                preparePhones(phones, homePhones, workPhones);
                req.setAttribute("homePhones", homePhones);
                req.setAttribute("workPhones", workPhones);
                req.getRequestDispatcher("/jsp/userprofile.jsp").forward(req, resp);
            }
        }
    }
}
