package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.web.util.JSPHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editprofilecheck")
public class EditProfileCeck extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSPHelper jspHelper = new JSPHelper(req);
        jspHelper.editProfile();
        resp.sendRedirect("/jsp/userprofile.jsp");
    }
}
