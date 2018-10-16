package com.getjavajob.simplenet.web.servlets.messages;

import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.service.MessageService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/chat")
public class ShowChat extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        this.messageService = ApplicationContextProvider.getApplicationContext().getBean(MessageService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int secondTalkerId = Integer.parseInt(req.getParameter("userId"));
        req.setAttribute("secondTalkerId", secondTalkerId);
        List<Message> chatMessages = messageService.getChatMessages(userId, secondTalkerId);
        Collections.sort(chatMessages);
        req.setAttribute("chatMessages", chatMessages);
        req.getRequestDispatcher("/jsp/messages/chat.jsp").forward(req, resp);
    }
}
