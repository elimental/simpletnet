package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.PersonalMessage;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final AccountService accountService;
    private final CommunityService communityService;

    @Autowired
    public MessageController(AccountService accountService, CommunityService communityService) {
        this.accountService = accountService;
        this.communityService = communityService;
    }

    @GetMapping("/messages")
    public ModelAndView showPersonalMessages(@SessionAttribute("userId") long userIdInSession) {
        logger.trace("User(id={}) is going to message list", userIdInSession);
        ModelAndView modelAndView = new ModelAndView("messages/personalMessages");
        modelAndView.addObject("talkers", accountService.getTalkersId(userIdInSession));
        return modelAndView;
    }

    @GetMapping("/chat")
    public ModelAndView showChat(@SessionAttribute("userId") long userIdInSession, long id) {
        logger.trace("User(id={}) is going to chat with user id={}", userIdInSession, id);
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
        logger.trace("User(id={}) sent personal message to user id={}", userIdInSession, secondTalkerId);
        return "redirect:/chat?id=" + secondTalkerId;
    }

    @GetMapping("/sendWallMessage")
    public String sendWallMessage(@SessionAttribute("userId") long userIdInSession, String message) {
        accountService.sendWallMessage(userIdInSession, message);
        logger.trace("User(id={}) sent wall message");
        return "redirect:/userProfile?id=" + userIdInSession;
    }

    @GetMapping("/deleteWallMessage")
    public String deleteWallMessage(@SessionAttribute("userId") long userIdInSession,
                                    long messageId, @RequestParam("returnId") long accountId) {
        accountService.deleteWallMessage(messageId, accountId);
        logger.trace("User(id={}) deleted wall message", userIdInSession, messageId);
        return "redirect:/userProfile?id=" + accountId;
    }

    @GetMapping("/sendCommunityMessage")
    public String sendCommunityMessage(@SessionAttribute("userId") long userIdInSession, long communityId, String message) {
        communityService.addCommunityMessage(communityId, userIdInSession, message);
        logger.trace("User(id={}) sent message to community id={}", userIdInSession, communityId);
        return "redirect:/community?id=" + communityId;
    }

    @GetMapping("/deleteCommunityMessage")
    public String deleteCommunityMessage(@SessionAttribute("userId") long userIdInSession,
                                         long messageId, @RequestParam("returnId") long communityId) {
        communityService.deleteCommunityMessage(messageId, communityId);
        logger.trace("User(id={}) deleted message from community id={}", userIdInSession, communityId);
        return "redirect:/community?id=" + communityId;
    }
}
