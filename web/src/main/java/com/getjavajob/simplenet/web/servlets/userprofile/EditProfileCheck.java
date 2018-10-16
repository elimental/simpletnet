package com.getjavajob.simplenet.web.servlets.userprofile;

import com.getjavajob.simplenet.web.util.ServletHelper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editProfileCheck")
public class EditProfileCheck extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletHelper servletHelper = new ServletHelper(req);
        int id = servletHelper.editProfile();
        resp.sendRedirect("/userProfile?id=" + id);
    }
}
