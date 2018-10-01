package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/groupAccessDenied")
public class GroupAccessDenied extends HttpServlet {
    private GroupService groupService = GroupService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.parseInt(req.getParameter("groupId"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        boolean alreadyRequested = groupService.ifAlreadyRequested(userId, groupId);
        req.setAttribute("alreadyRequested", alreadyRequested);
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/jsp/groups/groupaccessdenied.jsp").forward(req, resp);
    }
}
