package com.getjavajob.simplenet.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirmDelete")
public class ConfirmDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("id", id);
        if (type.equals("user")) {
            req.getRequestDispatcher("/jsp/confirmdeleteuser.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/jsp/confirmdeletegroup.jsp").forward(req, resp);
        }
    }
}
