package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/groups")
public class ShowUserGroups extends HttpServlet {

    private GroupService groupService;

    @Override
    public void init() throws ServletException {
        this.groupService = ApplicationContextProvider.getApplicationContext().getBean(GroupService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        List<Group> groups = groupService.getUserGroups(userId);
        req.setAttribute("groups", groups);
        req.getRequestDispatcher("/jsp/groups/groups.jsp").forward(req, resp);
    }
}