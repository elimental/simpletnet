package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.web.util.JSPHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/regcheck")
public class RegCheck extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSPHelper jspHelper = new JSPHelper(req);
        if (!jspHelper.registration()) {
            resp.sendRedirect("/jsp/regerror.jsp");
        } else {
            resp.sendRedirect("/jsp/regaccept.jsp");
        }
    }
}
