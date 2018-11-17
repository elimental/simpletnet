package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.common.entity.CommunityMessage;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes("userId")
public class CommunityController {

    private final CommunityService communityService;
    private final AccountService accountService;

    @Autowired
    public CommunityController(CommunityService communityService, AccountService accountService) {
        this.communityService = communityService;
        this.accountService = accountService;
    }

    @GetMapping("/communities")
    public ModelAndView showUserCommunities(@SessionAttribute("userId") long userIdInSession) {
        ModelAndView modelAndView = new ModelAndView("community/accountCommunities");
        List<Community> communities = communityService.getAccountCommunities(userIdInSession);
        List<Community> requestedCommunities = communityService.getRequestedCommunities(userIdInSession);
        modelAndView.addObject("communities", communities);
        modelAndView.addObject("requestedCommunities", requestedCommunities);
        return modelAndView;
    }

    @GetMapping("/community")
    public ModelAndView showGroupPage(@SessionAttribute("userId") long userIdInSession, long id) {
        Community community = communityService.getCommunityById(id);
        if (community == null) {
            return new ModelAndView("community/unknownCommunity");
        }
        ModelAndView modelAndView = new ModelAndView("community/community");
        boolean member = communityService.ifCommunityMember(userIdInSession, id);
        boolean admin = accountService.ifAdmin(userIdInSession);
        if (!member && !admin) {
            ModelAndView accessDenied = new ModelAndView("community/communityAccessDenied");
            boolean alreadyRequested = communityService.ifAlreadyRequested(userIdInSession, id);
            accessDenied.addObject("alreadyRequested", alreadyRequested);
            accessDenied.addObject("userId", userIdInSession);
            accessDenied.addObject("communityId", id);
            return accessDenied;
        }
        List<CommunityMessage> communityMessages = communityService.getCommunityMessages(id);
        boolean owner = communityService.ifCommunityCreator(userIdInSession, id);
        boolean moderator = communityService.ifCommunityModerator(userIdInSession, id);
        boolean delete = admin || owner;
        boolean edit = delete || moderator;
        boolean showMakeRequestButton = admin && !owner;
        boolean showModeratorContent = edit;
        boolean showExitButton = member && !owner;
        modelAndView.addObject("community", community);
        modelAndView.addObject("communityMessages", communityMessages);
        modelAndView.addObject("showExitButton", showExitButton);
        modelAndView.addObject("delete", delete);
        modelAndView.addObject("edit", edit);
        modelAndView.addObject("member", member);
        modelAndView.addObject("showMakeRequestButton", showMakeRequestButton);
        modelAndView.addObject("showModeratorContent", showModeratorContent);
        return modelAndView;
    }


    @GetMapping("/communityMembers")
    public ModelAndView showGroupMembers(@SessionAttribute("userId") long userIdInSession, long id) {
        ModelAndView modelAndView = new ModelAndView("community/communityMembers");
        boolean admin = accountService.ifAdmin(userIdInSession);
        boolean moderator = communityService.ifCommunityModerator(userIdInSession, id);
        boolean delete = admin || moderator;
        boolean owner = communityService.ifCommunityCreator(userIdInSession, id);
        boolean showCandidates = admin || moderator;
        List<Account> members = communityService.getMembers(id);
        List<Account> candidates = communityService.getCandidates(id);
        List<Account> moderators = communityService.getModerators(id);
        modelAndView.addObject("communityId", id);
        modelAndView.addObject("userId", userIdInSession);
        modelAndView.addObject("owner", owner);
        modelAndView.addObject("showCandidates", showCandidates);
        modelAndView.addObject("delete", delete);
        modelAndView.addObject("candidates", candidates);
        modelAndView.addObject("moderators", moderators);
        modelAndView.addObject("simpleMembers", members);
        return modelAndView;
    }

    @GetMapping("/createCommunity")
    public String createGroup() {
        return "community/createCommunity";
    }

    @PostMapping("/checkCreateGroup")
    public String checkCreateGroup(@SessionAttribute("userId") long userIdInSession,
                                   @RequestParam("img") MultipartFile img,
                                   @ModelAttribute Community community) throws IOException {
        if (!img.isEmpty()) {
            community.setPicture(img.getBytes());
        }
        communityService.addCommunity(community, userIdInSession);
        return "redirect:/communities";
    }

    @GetMapping("/editCommunity")
    public ModelAndView editGroup(long id) {
        ModelAndView modelAndView = new ModelAndView("community/editCommunity");
        modelAndView.addObject("community", communityService.getCommunityById(id));
        return modelAndView;
    }

    @PostMapping("/checkCommunityEdit")
    public String checkEditGroup(@RequestParam("img") MultipartFile img,
                                 @ModelAttribute Community community) throws IOException {
        if (!img.isEmpty()) {
            community.setPicture(img.getBytes());
        }
        communityService.updateCommunity(community);
        return "redirect:/community?id=" + community.getId();
    }

    @GetMapping("/confirmDeleteCommunity")
    public String confirmDeleteUserProfile(long id, Model model) {
        model.addAttribute("id", id);
        return "community/confirmDeleteCommunity";
    }

    @GetMapping("/deleteCommunity")
    public String deleteGroup(@SessionAttribute("userId") long userIdInSession, long id) {
        boolean admin = accountService.ifAdmin(userIdInSession);
        boolean moderator = communityService.ifCommunityModerator(userIdInSession, id);
        boolean allowDeleteGroup = moderator || admin;
        if (!allowDeleteGroup) {
            return "accessDenied";
        } else {
            communityService.deleteCommunity(id);
            return "community/communityDeleted";
        }
    }

    @GetMapping("/exitFromCommunity")
    public String exitFromGroup(@SessionAttribute("userId") long userIdInSession, long id) {
        accountService.exitFromCommunity(userIdInSession, id);
        return "redirect:/communities";
    }

    @GetMapping("/deleteFromCommunity")
    public String deleteFromCommunity(long userId, long communityId) {
        accountService.exitFromCommunity(userId, communityId);
        return "redirect:/communityMembers?id=" + communityId;
    }

    @GetMapping("/acceptCommunityRequest")
    public String acceptGroupRequest(long userId, long communityId) {
        communityService.acceptCommunityRequest(userId, communityId);
        return "redirect:/communityMembers?id=" + communityId;
    }

    @GetMapping("/communityRequest")
    public String sendRequestToGroup(@SessionAttribute("userId") long userIdInSession, long groupId) {
        communityService.sendCommunityRequest(userIdInSession, groupId);
        return "redirect:/communities";
    }

    @GetMapping("/makeModerator")
    public String makeModerator(@SessionAttribute("userId") long adminOrModeratorId, long userId, long communityId) {
        boolean admin = accountService.ifAdmin(adminOrModeratorId);
        boolean moderator = communityService.ifCommunityModerator(adminOrModeratorId, communityId);
        if (!admin && !moderator) {
            return "accessDenied";
        } else {
            communityService.makeModerator(userId, communityId);
            return "redirect:/communityMembers?id=" + communityId;
        }
    }

    @GetMapping("/rejectCommunityRequest")
    public String rejectGroupRequest(long userId, long communityId) {
        communityService.rejectCommunityRequest(userId, communityId);
        return "redirect:/communityMembers?id=" + communityId;
    }
}
