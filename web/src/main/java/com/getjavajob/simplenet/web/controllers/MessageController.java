package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes("userId")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public ModelAndView showPersonalMessages(@SessionAttribute("userId") int userIdInSession) {
        ModelAndView modelAndView = new ModelAndView("messages/personalMessages");
        modelAndView.addObject("talkers", messageService.getTalkersId(userIdInSession));
        return modelAndView;
    }

    @GetMapping("/chat")
    public ModelAndView showChat(@SessionAttribute("userId") int userIdInSession, int id) {
        ModelAndView modelAndView = new ModelAndView("messages/chat");
        List<Message> chatMessages = messageService.getChatMessages(userIdInSession, id);
        Collections.sort(chatMessages);
        modelAndView.addObject("secondTalkerId", id);
        modelAndView.addObject("chatMessages", chatMessages);
        return modelAndView;
    }

    @GetMapping("/deleteMessage")
    public String deleteMessage(String type, int messageId, int returnId) {
        messageService.deleteMessage(messageId);
        if (type.equals("group")) {
            return "redirect:/group?id=" + returnId;
        } else {
            return "redirect:/userProfile?id=" + returnId;
        }
    }

    @GetMapping("/sendGroupMessage")
    public String sendGroupMessage(@SessionAttribute("userId") int userIdInSession, String message, int groupId) {
        messageService.sendGroupMessage(userIdInSession, groupId, message);
        return "redirect:/group?id=" + groupId;
    }

    @GetMapping("/sendPersonalMessage")
    public String sendPersonalMessage(@SessionAttribute("userId") int userIdInSession, String message,
                                      int secondTalkerId) {
        messageService.sendPersonalMessage(userIdInSession, secondTalkerId, message);
        return "redirect:/chat?id=" + secondTalkerId;
    }

    @GetMapping("/sendWallMessage")
    public String sendWallMessage(@SessionAttribute("userId") int userIdInSession, String message) {
        messageService.sendWallMessage(userIdInSession, message);
        return "redirect:/userProfile?id=" + userIdInSession;
    }
}
