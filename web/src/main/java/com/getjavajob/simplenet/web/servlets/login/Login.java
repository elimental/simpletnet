package com.getjavajob.simplenet.web.servlets.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            int userId = (Integer) session.getAttribute("userId");
            resp.sendRedirect("/userProfile?id=" + userId);
        } else {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                Map<String, String> cookiesMap = new HashMap<>();
                for (Cookie cookie : cookies) {
                    cookiesMap.put(cookie.getName(), cookie.getValue());
                }
                String email = cookiesMap.get("email");
                String password = cookiesMap.get("password");
                if (email != null && password != null) {
                    resp.sendRedirect("/loginCheck");
                } else {
                    req.getRequestDispatcher("/jsp/login/login.jsp").forward(req, resp);
                }
            } else {
                req.getRequestDispatcher("/jsp/login/login.jsp").forward(req, resp);
            }
        }
    }
}
