package com.getjavajob.simplenet.web.servlets.messages;

import com.getjavajob.simplenet.service.MessageService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteMessage")
public class DeleteMessage extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        this.messageService = ApplicationContextProvider.getApplicationContext().getBean(MessageService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int messageId = Integer.parseInt(req.getParameter("messageId"));
        String type = req.getParameter("type");
        int returnId = Integer.parseInt(req.getParameter("returnId"));
        messageService.deleteMessage(messageId);
        if (type.equals("group")) {
            resp.sendRedirect("/group?id=" + returnId);
        } else {
            resp.sendRedirect("/userProfile?id=" + returnId);
        }
    }
}
