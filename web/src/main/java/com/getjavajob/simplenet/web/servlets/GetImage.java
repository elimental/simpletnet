package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/getImage")
public class GetImage extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        Account account = accountService.getUserById(userId);
        byte[] photo = account.getPhoto();
        resp.setContentType("image/jpg");
        ServletOutputStream outputStream = resp.getOutputStream();
        if (photo == null) {
            String filePath = req.getServletContext().getRealPath("/pic/nophoto.jpg");
            outputStream.write(FileUtils.readFileToByteArray(new File(filePath)));
        } else {
            outputStream.write(photo);
        }
        outputStream.close();
    }
}
