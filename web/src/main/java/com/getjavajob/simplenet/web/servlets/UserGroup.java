package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/group")
public class UserGroup extends HttpServlet {
    private GroupService groupService = new GroupService();
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.parseInt(req.getParameter("id"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        boolean member = groupService.ifGroupMember(userId, groupId);
        boolean admin = accountService.ifAdmin(userId);
        if (!member && !admin) {
            req.getRequestDispatcher("/groupAccessDenied?id=" + groupId).forward(req, resp);
        }
        boolean owner = groupService.ifGroupOwner(userId, groupId);
        boolean moderator = groupService.ifGroupModerator(userId, groupId);
        boolean delete = admin || owner;
        req.setAttribute("delete", delete);
        boolean edit = delete || moderator;
        req.setAttribute("edit", edit);
        boolean showMakeRequestButton = admin && !owner;
        req.setAttribute("showMakeRequestButton", showMakeRequestButton);
        Group group = groupService.getGroupById(groupId);
        req.setAttribute("group", group);
        req.getRequestDispatcher("/jsp/group.jsp").forward(req, resp);
    }
}
