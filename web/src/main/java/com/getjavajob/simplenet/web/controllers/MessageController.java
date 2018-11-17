package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.PersonalMessage;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes("userId")
public class MessageController {

    private final AccountService accountService;
    private final CommunityService communityService;

    @Autowired
    public MessageController(AccountService accountService, CommunityService communityService) {
        this.accountService = accountService;
        this.communityService = communityService;
    }

    @GetMapping("/messages")
    public ModelAndView showPersonalMessages(@SessionAttribute("userId") long userIdInSession) {
        ModelAndView modelAndView = new ModelAndView("messages/personalMessages");
        modelAndView.addObject("talkers", accountService.getTalkersId(userIdInSession));
        return modelAndView;
    }

    @GetMapping("/chat")
    public ModelAndView showChat(@SessionAttribute("userId") long userIdInSession, long id) {
        ModelAndView modelAndView = new ModelAndView("messages/chat");
        List<PersonalMessage> chatMessages = accountService.getChatMessages(userIdInSession, id);
        Collections.sort(chatMessages);
        modelAndView.addObject("secondTalkerId", id);
        modelAndView.addObject("chatMessages", chatMessages);
        return modelAndView;
    }


    @GetMapping("/sendPersonalMessage")
    public String sendPersonalMessage(@SessionAttribute("userId") long userIdInSession, String message,
                                      long secondTalkerId) {
        accountService.sendPersonalMessage(userIdInSession, secondTalkerId, message);
        return "redirect:/chat?id=" + secondTalkerId;
    }

    @GetMapping("/sendWallMessage")
    public String sendWallMessage(@SessionAttribute("userId") long userIdInSession, String message) {
        accountService.sendWallMessage(userIdInSession, message);
        return "redirect:/userProfile?id=" + userIdInSession;
    }

    @GetMapping("/deleteWallMessage")
    public String deleteWallMessage(long messageId, @RequestParam("returnId") long accountId) {
        accountService.deleteWallMessage(messageId, accountId);
        return "redirect:/userProfile?id=" + accountId;
    }

    @GetMapping("/sendCommunityMessage")
    public String sendCommunityMessage(@SessionAttribute("userId") long userIdInSession, long groupId, String message) {
        communityService.addCommunityMessage(groupId, userIdInSession, message);
        return "redirect:/community?id=" + groupId;
    }

    @GetMapping("/deleteCommunityMessage")
    public String deleteCommunityMessage(long messageId, @RequestParam("returnId") long groupId) {
        communityService.deleteCommunityMessage(messageId, groupId);
        return "redirect:/community?id=" + groupId;
    }
}
