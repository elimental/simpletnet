package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.web.util.ServletHelper;
import com.sun.org.apache.xpath.internal.operations.String;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/regCheck")
public class RegCheck extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper servletHelper = new ServletHelper(req);
        if (!servletHelper.registration()) {
            resp.sendRedirect("/regError");
        } else {
            resp.sendRedirect("/regAccept");
        }
    }
}
