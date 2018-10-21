package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

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

    private AccountService accountService;
    private GroupService groupService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
        this.groupService = ApplicationContextProvider.getApplicationContext().getBean(GroupService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.parseInt(req.getParameter("groupId"));
        req.setAttribute("groupId", groupId);
        int userId = (Integer) req.getSession().getAttribute("userId");
        req.setAttribute("userId", userId);
        boolean admin = accountService.ifAdmin(userId);
        boolean moderator = groupService.ifGroupModerator(userId, groupId);
        boolean delete = admin || moderator;
        boolean owner = groupService.ifGroupOwner(userId, groupId);
        req.setAttribute("owner", owner);
        boolean showCandidates = admin || moderator;
        req.setAttribute("showCandidates", showCandidates);
        req.setAttribute("delete", delete);
        List<Account> members = groupService.getMembers(groupId);
        List<Account> candidates = groupService.getCandidates(groupId);
        req.setAttribute("candidates", candidates);
        List<Account> moderators = new ArrayList<>();
        List<Account> simpleMembers = new ArrayList<>();
        for (Account account : members) {
            int id = account.getId();
            if (groupService.ifGroupModerator(id, groupId) || accountService.ifAdmin(id)) {
                moderators.add(account);
            } else {
                simpleMembers.add(account);
            }
        }
        req.setAttribute("moderators", moderators);
        req.setAttribute("simpleMembers", simpleMembers);
        req.getRequestDispatcher("/jsp/groups/groupmembers.jsp").forward(req, resp);
    }
}
