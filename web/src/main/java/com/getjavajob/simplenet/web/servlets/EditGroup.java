package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editGroup")
public class EditGroup extends HttpServlet {
    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Group group = groupService.getGroupById(id);
        req.setAttribute("group", group);
        req.getRequestDispatcher("/jsp/editgroup.jsp").forward(req, resp);
    }
}
