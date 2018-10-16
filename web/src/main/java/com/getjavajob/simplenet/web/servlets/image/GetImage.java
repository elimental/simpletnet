package com.getjavajob.simplenet.web.servlets.image;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;
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
    private static final String USER_DEFAULT_IMAGE_PATH = "/pic/nophoto.jpg";
    private static final String GROUP_DEFAULT_IMAGE_PATH = "/pic/group_no_photo.jpg";

    private AccountService accountService;
    private GroupService groupService;

    @Override
    public void init() throws ServletException {
        this.accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
        this.groupService = ApplicationContextProvider.getApplicationContext().getBean(GroupService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String type = req.getParameter("type");
        int id = Integer.parseInt(req.getParameter("id"));
        byte[] img = null;
        String defaultPath = null;
        if (type.equals("user")) {
            Account account = accountService.getUserById(id);
            img = account.getPhoto();
            defaultPath = USER_DEFAULT_IMAGE_PATH;
        } else if (type.equals("group")) {
            Group group = groupService.getGroupById(id);
            img = group.getPicture();
            defaultPath = GROUP_DEFAULT_IMAGE_PATH;
        }
        resp.setContentType("image/jpg");
        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            if (img == null) {
                String filePath = req.getServletContext().getRealPath(defaultPath);
                outputStream.write(FileUtils.readFileToByteArray(new File(filePath)));
            } else {
                outputStream.write(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
