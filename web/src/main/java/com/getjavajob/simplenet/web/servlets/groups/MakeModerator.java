package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/makeModerator")
public class MakeModerator extends HttpServlet {

    private AccountService accountService;
    private GroupService groupService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
        this.groupService = ApplicationContextProvider.getApplicationContext().getBean(GroupService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int adminOrModeratorId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));
        boolean admin = accountService.ifAdmin(adminOrModeratorId);
        boolean moderator = groupService.ifGroupModerator(adminOrModeratorId, groupId);
        if (!admin && !moderator) {
            resp.sendRedirect("/accessDenied");
        } else {
            int userId = Integer.parseInt(req.getParameter("userId"));
            groupService.makeModerator(userId, groupId);
            resp.sendRedirect("/groupMembers?groupId=" + groupId);
        }
    }
}
