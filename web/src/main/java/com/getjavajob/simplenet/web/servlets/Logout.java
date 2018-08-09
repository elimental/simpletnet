package com.getjavajob.simplenet.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie emailCookie = new Cookie("email", "");
        emailCookie.setMaxAge(0);
        Cookie passCookie = new Cookie("password", "");
        passCookie.setMaxAge(0);
        resp.addCookie(emailCookie);
        resp.addCookie(passCookie);
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("/jsp/login.jsp");
    }
}
