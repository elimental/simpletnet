package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.common.entity.CommunityMessage;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);

    private final CommunityService communityService;
    private final AccountService accountService;

    @Autowired
    public CommunityController(CommunityService communityService, AccountService accountService) {
        this.communityService = communityService;
        this.accountService = accountService;
    }

    @GetMapping("/communities")
    public ModelAndView showUserCommunities(@SessionAttribute("userId") long userIdInSession) {
        logger.trace("User(id={}) is going to community list", userIdInSession);
        ModelAndView modelAndView = new ModelAndView("community/accountCommunities");
        List<Community> communities = communityService.getAccountCommunities(userIdInSession);
        List<Community> requestedCommunities = communityService.getRequestedCommunities(userIdInSession);
        modelAndView.addObject("communities", communities);
        modelAndView.addObject("requestedCommunities", requestedCommunities);
        return modelAndView;
    }

    @GetMapping("/community")
    public ModelAndView showGroupPage(@SessionAttribute("userId") long userIdInSession, long id) {
        logger.trace("User(id={}) is going to community id={}", userIdInSession, id);
        Community community = communityService.getCommunityById(id);
        if (community == null) {
            logger.error("User(id={}) is trying to get nonexistent community id={}", userIdInSession, id);
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
        logger.trace("User(id={}) is going to community's id={} member list", userIdInSession, id);
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
        logger.trace("User(id={}) created community {}", userIdInSession, community.getName());
        return "redirect:/communities";
    }

    @GetMapping("/editCommunity")
    public ModelAndView editGroup(@SessionAttribute("userId") long userIdInSession, long id) {
        logger.trace("User(id={}) is trying to edit community id={}", userIdInSession, id);
        ModelAndView modelAndView = new ModelAndView("community/editCommunity");
        modelAndView.addObject("community", communityService.getCommunityById(id));
        return modelAndView;
    }

    @PostMapping("/checkCommunityEdit")
    public String checkEditGroup(@SessionAttribute("userId") long userIdInSession,
                                 @RequestParam("img") MultipartFile img,
                                 @ModelAttribute Community community) throws IOException {
        if (!img.isEmpty()) {
            community.setPicture(img.getBytes());
        }
        communityService.updateCommunity(community);
        logger.trace("User(id={}) edited community id={}", userIdInSession, community.getId());
        return "redirect:/community?id=" + community.getId();
    }

    @GetMapping("/confirmDeleteCommunity")
    public String confirmDeleteUserProfile(@SessionAttribute("userId") long userIdInSession, long id, Model model) {
        model.addAttribute("id", id);
        logger.trace("User(id={}) is trying to delete community id={}", userIdInSession, id);
        return "community/confirmDeleteCommunity";
    }

    @GetMapping("/deleteCommunity")
    public String deleteGroup(@SessionAttribute("userId") long userIdInSession, long id) {
        boolean admin = accountService.ifAdmin(userIdInSession);
        boolean moderator = communityService.ifCommunityModerator(userIdInSession, id);
        boolean allowDeleteGroup = moderator || admin;
        if (!allowDeleteGroup) {
            logger.error("Illegal operation. User(id={}) is not allowed to delete community id={}",
                    userIdInSession, id);
            return "accessDenied";
        } else {
            communityService.deleteCommunity(id);
            return "community/communityDeleted";
        }
    }

    @GetMapping("/exitFromCommunity")
    public String exitFromGroup(@SessionAttribute("userId") long userIdInSession, long id) {
        accountService.exitFromCommunity(userIdInSession, id);
        logger.trace("User(id={}) is going out from cummunity id={}", userIdInSession, id);
        return "redirect:/communities";
    }

    @GetMapping("/deleteFromCommunity")
    public String deleteFromCommunity(@SessionAttribute("userId") long userIdInSession, long userId, long communityId) {
        accountService.exitFromCommunity(userId, communityId);
        logger.trace("User(id={}) deleted user id={} from community id={}", userIdInSession, userId, communityId);
        return "redirect:/communityMembers?id=" + communityId;
    }

    @GetMapping("/acceptCommunityRequest")
    public String acceptGroupRequest(@SessionAttribute("userId") long userIdInSession, long userId, long communityId) {
        communityService.acceptCommunityRequest(userId, communityId);
        logger.trace("User(id={}) accepted user's id={} request to community id={}", userIdInSession, userId, communityId);
        return "redirect:/communityMembers?id=" + communityId;
    }

    @GetMapping("/communityRequest")
    public String sendRequestToGroup(@SessionAttribute("userId") long userIdInSession, long communityId) {
        communityService.sendCommunityRequest(userIdInSession, communityId);
        logger.trace("User(id={}) sent request to community id={}", userIdInSession, communityId);
        return "redirect:/communities";
    }

    @GetMapping("/makeModerator")
    public String makeModerator(@SessionAttribute("userId") long adminOrModeratorId, long userId, long communityId) {
        boolean admin = accountService.ifAdmin(adminOrModeratorId);
        boolean moderator = communityService.ifCommunityModerator(adminOrModeratorId, communityId);
        if (!admin && !moderator) {
            logger.error("Illegal operation. User(id={}) is not allowed to give to user" +
                    " id={} moderator role community id={}", adminOrModeratorId, userId, communityId);
            return "accessDenied";
        } else {
            communityService.makeModerator(userId, communityId);
            logger.trace("User(id={}) gave to user id={} moderator role in community id={}",
                    adminOrModeratorId, userId, communityId);
            return "redirect:/communityMembers?id=" + communityId;
        }
    }

    @GetMapping("/rejectCommunityRequest")
    public String rejectGroupRequest(@SessionAttribute("userId") long userIdInSession, long userId, long communityId) {
        communityService.rejectCommunityRequest(userId, communityId);
        logger.trace("User(id={}) rejected request from user id={} to community id={}", userId, userId, communityId);
        return "redirect:/communityMembers?id=" + communityId;
    }
}
