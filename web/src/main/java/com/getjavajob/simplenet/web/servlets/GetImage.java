package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Picture;
import com.getjavajob.simplenet.dao.PicturesDAO;
import com.getjavajob.simplenet.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getimage")
public class GetImage extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        this.accountService = new AccountService(new PicturesDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        Picture picture = accountService.getPhoto(userId);
        if (picture == null) {
            resp.getWriter().write("/pic/nophoto.jpg");
        } else {
            resp.setContentType("image/jpg");
            ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.write(picture.getFileData());
            outputStream.close();
        }
    }
}
