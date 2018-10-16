package com.getjavajob.simplenet.web.servlets.messages;

import com.getjavajob.simplenet.service.MessageService;
import com.getjavajob.simplenet.web.util.ApplicationContextProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/messages")
public class ShowPersonalMessages extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        this.messageService = ApplicationContextProvider.getApplicationContext().getBean(MessageService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        List<Integer> talkersId = messageService.getTalkersId(userId);
        req.setAttribute("talkersId", talkersId);
        req.getRequestDispatcher("/jsp/messages/messages.jsp").forward(req, resp);
    }
}
