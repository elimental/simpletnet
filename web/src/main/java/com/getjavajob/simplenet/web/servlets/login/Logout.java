package com.getjavajob.simplenet.web.servlets.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.simplenet.web.util.ServletHelper.deleteCookies;
import static com.getjavajob.simplenet.web.util.ServletHelper.invalidateSession;

@WebServlet("/logout")
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deleteCookies(resp);
        invalidateSession(req);
        resp.sendRedirect("/login");
    }
}
