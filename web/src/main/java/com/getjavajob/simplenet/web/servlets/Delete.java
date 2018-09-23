package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete")
public class Delete extends HttpServlet {
    private AccountService accountService = new AccountService();
    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        int idInSession = (Integer) session.getAttribute("userId");
        boolean admin = accountService.ifAdmin(idInSession);
        boolean owner = id == idInSession;
        boolean allowDeleteUser = admin || owner;
        if (type.equals("user")) {
            if (!allowDeleteUser) {
                resp.sendRedirect("/accessDenied");
            } else {
                accountService.deleteAccount(id);
                if (owner) {
                    session.removeAttribute("userId");
                    Cookie emailCookie = new Cookie("email", "");
                    emailCookie.setMaxAge(0);
                    Cookie passCookie = new Cookie("password", "");
                    passCookie.setMaxAge(0);
                    resp.addCookie(emailCookie);
                    resp.addCookie(passCookie);
                    session.invalidate();
                    resp.sendRedirect("/selfProfileDelete");
                } else {
                    resp.sendRedirect("/profileDeleted");
                }
            }
        } else {
            boolean moderator = groupService.ifGroupModerator(idInSession, id);
            boolean allowDeleteGroup = moderator || admin;
            if (!allowDeleteGroup) {
                resp.sendRedirect("/accessDenied");
            } else {
                groupService.deleteGroup(id);
                resp.sendRedirect("/groupDeleted");
            }
        }
    }
}
