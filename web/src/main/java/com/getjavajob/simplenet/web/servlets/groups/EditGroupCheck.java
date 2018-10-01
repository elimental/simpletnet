package com.getjavajob.simplenet.web.servlets.groups;

import com.getjavajob.simplenet.web.util.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editGroupCheck")
public class EditGroupCheck extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper servletHelper = new ServletHelper(req);
        int id = servletHelper.updateGroup();
        resp.sendRedirect("/group?id=" + id);
    }
}
