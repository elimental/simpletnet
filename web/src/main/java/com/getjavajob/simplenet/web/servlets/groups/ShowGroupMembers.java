package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/groupMembers")
public class ShowGroupMembers extends HttpServlet {
    private GroupService groupService = GroupService.getInstance();
    private AccountService accountService = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.parseInt(req.getParameter("groupId"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        boolean admin = accountService.ifAdmin(userId);
        boolean moderator = groupService.ifGroupModerator(userId, groupId);
        boolean delete = admin || moderator;
        boolean owner = groupService.ifGroupOwner(userId, groupId);
        req.setAttribute("delete", delete);
        List<Account> members = groupService.getMembers(groupId);
        List<Account> adminsAndModerators = new ArrayList<>();
        List<Account> simpleMembers = new ArrayList<>();
        for (Account account : members) {
            int id = account.getId();
            if (accountService.ifAdmin(id) || groupService.ifGroupModerator(id, groupId)) {
                adminsAndModerators.add(account);
            } else {
                simpleMembers.add(account);
            }
        }
        req.setAttribute("userId", userId);
        req.setAttribute("owner", owner);
        req.setAttribute("adminsAndModerators", adminsAndModerators);
        req.setAttribute("simpleMembers", simpleMembers);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/jsp/groups/groupmembers.jsp").forward(req, resp);
    }
}
