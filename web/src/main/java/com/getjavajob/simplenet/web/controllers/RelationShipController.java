package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes("userId")
public class RelationShipController {

    private final AccountService accountService;

    @Autowired
    public RelationShipController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/friends")
    public ModelAndView showFriends(@SessionAttribute("userId") long userIdInSession) {
        ModelAndView modelAndView = new ModelAndView("friends/friends");
        List<Account> friends = accountService.getFriends(userIdInSession);
        List<Account> requestedFriends = accountService.getRequestedFriends(userIdInSession);
        List<Account> requestFromFriends = accountService.getRequestFromFriends(userIdInSession);
        modelAndView.addObject("friends", friends);
        modelAndView.addObject("requestedFriends", requestedFriends);
        modelAndView.addObject("requestFromFriends", requestFromFriends);
        return modelAndView;
    }


    @GetMapping("/acceptFriendRequest")
    public String acceptFriendRequest(@SessionAttribute("userId") long whoAcceptsId,
                                      @RequestParam("id") long whoAcceptedId) {
        accountService.acceptFriendRequest(whoAcceptsId, whoAcceptedId);
        return "redirect:/friends";
    }

    @GetMapping("/deleteFriend")
    public String deleteFriend(@SessionAttribute("userId") long userOneId,
                               @RequestParam("id") long userTwoId) {
        accountService.deleteFriend(userOneId, userTwoId);
        return "redirect:/friends";
    }

    @GetMapping("/friendRequest")
    public String sendFriendRequest(@SessionAttribute("userId") long fromUserId,
                                    @RequestParam("id") long toUserId) {
        accountService.sendFriendRequest(fromUserId, toUserId);
        return "redirect:/friends";
    }
}
