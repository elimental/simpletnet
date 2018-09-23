package com.getjavajob.simplenet.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/groupAccessDenied")
public class GroupAccessDenied extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("id", groupId);
        req.getRequestDispatcher("/jsp/groupaccessdenied.jsp").forward(req, resp);
    }
}
