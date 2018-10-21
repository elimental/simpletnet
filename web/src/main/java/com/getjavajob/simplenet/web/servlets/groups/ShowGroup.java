package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import com.getjavajob.simplenet.service.MessageService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/group")
public class ShowGroup extends HttpServlet {

    private GroupService groupService;
    private AccountService accountService;
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        this.groupService = ApplicationContextProvider.getApplicationContext().getBean(GroupService.class);
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
        this.messageService = ApplicationContextProvider.getApplicationContext().getBean(MessageService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.parseInt(req.getParameter("id"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        boolean member = groupService.ifGroupMember(userId, groupId);
        boolean admin = accountService.ifAdmin(userId);
        if (!member && !admin) {
            req.getRequestDispatcher("/groupAccessDenied?groupId=" + groupId).forward(req, resp);
        }
        boolean owner = groupService.ifGroupOwner(userId, groupId);
        req.setAttribute("owner", owner);
        boolean moderator = groupService.ifGroupModerator(userId, groupId);
        boolean delete = admin || owner;
        req.setAttribute("delete", delete);
        boolean edit = delete || moderator;
        req.setAttribute("edit", edit);
        boolean showMakeRequestButton = admin && !owner;
        req.setAttribute("showMakeRequestButton", showMakeRequestButton);
        boolean showModeratorContent = edit;
        req.setAttribute("showModeratorContent", showModeratorContent);
        List<Message> messages = messageService.getGroupMessages(groupId);
        req.setAttribute("groupMessages", messages);
        Group group = groupService.getGroupById(groupId);
        req.setAttribute("group", group);
        req.getRequestDispatcher("/jsp/groups/group.jsp").forward(req, resp);
    }
}
