package com.getjavajob.simplenet.web.servlets.messages;

import com.getjavajob.simplenet.service.MessageService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sendWallMessage")
public class SendWallMessage extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        this.messageService = ApplicationContextProvider.getApplicationContext().getBean(MessageService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        String text = req.getParameter("message");
        if (!text.trim().isEmpty()) {
            messageService.sendWallMessage(userId, text);
        }
        resp.sendRedirect("/userProfile?id=" + userId);
    }
}
