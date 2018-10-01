package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/acceptGroupRequest")
public class AcceptGroupRequest extends HttpServlet {
    private GroupService groupService = GroupService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int groupId = Integer.parseInt(req.getParameter("groupId"));
        groupService.acceptGroupRequest(userId, groupId);
        resp.sendRedirect("/group?id=" + groupId);
    }
}
