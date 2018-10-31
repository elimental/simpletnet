package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import com.getjavajob.simplenet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/groups")
    public ModelAndView showUserGroups(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("groups/userGroups");
        int userIdInSession = (Integer) session.getAttribute("userId");
        List<Group> groups = groupService.getUserGroups(userIdInSession);
        modelAndView.addObject("groups", groups);
        return modelAndView;
    }

    @GetMapping("/group")
    public ModelAndView showGroupPage(int id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("groups/group");
        int userIdInSession = (Integer) session.getAttribute("userId");
        boolean member = groupService.ifGroupMember(userIdInSession, id);
        boolean admin = accountService.ifAdmin(userIdInSession);
        if (!member && !admin) {
            ModelAndView accessDenied = new ModelAndView("groups/groupAccessDenied");
            boolean alreadyRequested = groupService.ifAlreadyRequested(userIdInSession, id);
            accessDenied.addObject("alreadyRequested", alreadyRequested);
            accessDenied.addObject("userId", userIdInSession);
            accessDenied.addObject("groupId", id);
            return accessDenied;
        }
        Group group = groupService.getGroupById(id);
        List<Message> groupMessages = messageService.getGroupMessages(id);
        boolean owner = groupService.ifGroupOwner(userIdInSession, id);
        boolean moderator = groupService.ifGroupModerator(userIdInSession, id);
        boolean delete = admin || owner;
        boolean edit = delete || moderator;
        boolean showMakeRequestButton = admin && !owner;
        boolean showModeratorContent = edit;
        modelAndView.addObject("group", group);
        modelAndView.addObject("groupMessages", groupMessages);
        modelAndView.addObject("owner", owner);
        modelAndView.addObject("delete", delete);
        modelAndView.addObject("edit", edit);
        modelAndView.addObject("showMakeRequestButton", showMakeRequestButton);
        modelAndView.addObject("showModeratorContent", showModeratorContent);
        return modelAndView;
    }

    @GetMapping("/groupMembers")
    public ModelAndView showGroupMembers(int id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("groups/groupMembers");
        int userIdInSession = (Integer) session.getAttribute("userId");
        boolean admin = accountService.ifAdmin(userIdInSession);
        boolean moderator = groupService.ifGroupModerator(userIdInSession, id);
        boolean delete = admin || moderator;
        boolean owner = groupService.ifGroupOwner(userIdInSession, id);
        boolean showCandidates = admin || moderator;
        List<Account> members = groupService.getMembers(id);
        List<Account> candidates = groupService.getCandidates(id);
        List<Account> moderators = new ArrayList<>();
        List<Account> simpleMembers = new ArrayList<>();
        for (Account account : members) {
            int userId = account.getId();
            if (groupService.ifGroupModerator(userId, id) || accountService.ifAdmin(userId)) {
                moderators.add(account);
            } else {
                simpleMembers.add(account);
            }
        }
        modelAndView.addObject("groupId", id);
        modelAndView.addObject("userId", userIdInSession);
        modelAndView.addObject("owner", owner);
        modelAndView.addObject("showCandidates", showCandidates);
        modelAndView.addObject("delete", delete);
        modelAndView.addObject("candidates", candidates);
        modelAndView.addObject("moderators", moderators);
        modelAndView.addObject("simpleMembers", simpleMembers);
        return modelAndView;
    }

    @GetMapping("/createGroup")
    public String createGroup() {
        return "groups/createGroup";
    }

    @PostMapping("/checkCreateGroup")
    public String checkCreateGroup(@RequestParam("img") MultipartFile img,
                                   @ModelAttribute Group group, HttpSession session) throws IOException {
        if (!img.isEmpty()) {
            group.setPicture(img.getBytes());
        }
        group.setCreateDate(new Date(System.currentTimeMillis()));
        group.setOwner((Integer) session.getAttribute("userId"));
        groupService.addGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/editGroup")
    public ModelAndView editGroup(int id) {
        ModelAndView modelAndView = new ModelAndView("groups/editGroup");
        modelAndView.addObject("group", groupService.getGroupById(id));
        return modelAndView;
    }

    @PostMapping("/checkEditGroup")
    public String checkEditGroup(@RequestParam("img") MultipartFile img,
                                 @ModelAttribute Group group) throws IOException {
        int id = group.getId();
        Group groupFromDB = groupService.getGroupById(id);
        group.setOwner(groupFromDB.getOwner());
        group.setCreateDate(groupFromDB.getCreateDate());
        if (img.isEmpty()) {
            group.setPicture(groupFromDB.getPicture());
        } else {
            group.setPicture(img.getBytes());
        }
        groupService.updateGroup(group);
        return "redirect:/group?id=" + id;
    }

    @GetMapping("/confirmDeleteGroup")
    public String confirmDeleteUserProfile(int id, Model model) {
        model.addAttribute("id", id);
        return "groups/confirmDeleteGroup";
    }

    @GetMapping("/deleteGroup")
    public String deleteGroup(int id, HttpSession session) {
        int userIdInSession = (Integer) session.getAttribute("userId");
        boolean admin = accountService.ifAdmin(userIdInSession);
        boolean moderator = groupService.ifGroupModerator(userIdInSession, id);
        boolean allowDeleteGroup = moderator || admin;
        if (!allowDeleteGroup) {
            return "accessDenied";
        } else {
            groupService.deleteGroup(id);
            return "groups/groupDeleted";
        }
    }

    @GetMapping("/exitFromGroup")
    public String exitFromGroup(int groupId, int userId) {
        groupService.deleteFromGroup(userId, groupId);
        return "redirect:/groups";
    }

    @GetMapping("/acceptGroupRequest")
    public String acceptGroupRequest(int userId, int groupId) {
        groupService.acceptGroupRequest(userId, groupId);
        return "redirect:/groupMembers?groupId=" + groupId;
    }

    @GetMapping("/groupRequest")
    public String sendRequestToGroup(int userId, int groupId) {
        groupService.sendGroupRequest(userId, groupId);
        return "redirect:/groups";
    }

    @GetMapping("/makeModerator")
    public String makeModerator(int groupId, int userId, HttpSession session) {
        int adminOrModeratorId = (Integer) session.getAttribute("userId");
        boolean admin = accountService.ifAdmin(adminOrModeratorId);
        boolean moderator = groupService.ifGroupModerator(adminOrModeratorId, groupId);
        if (!admin && !moderator) {
            return "accessDenied";
        } else {
            groupService.makeModerator(userId, groupId);
            return "redirect:/groupMembers?id=" + groupId;
        }
    }

    @GetMapping("/rejectGroupRequest")
    public String rejectGroupRequest(int userId, int groupId) {
        groupService.rejectGroupRequest(userId, groupId);
        return "redirect:/groupMembers?id=" + groupId;
    }

    @GetMapping("/acceptFriendRequest")
    public String acceptFriendRequest(@RequestParam("id") int whoAcceptedId, HttpSession session) {
        int whoAcceptsId = (Integer) session.getAttribute("userId");
        accountService.acceptFriendRequest(whoAcceptsId, whoAcceptedId);
        return "redirect:/friends";
    }

    @GetMapping("/deleteFriend")
    public String deleteFriend(@RequestParam("id") int userTwoId, HttpSession session) {
        int userOneId = (Integer) session.getAttribute("userId");
        accountService.deleteFriend(userOneId, userTwoId);
        return "redirect:/friends";
    }

    @GetMapping("/friendRequest")
    public String sendFriendRequest(@RequestParam("id") int toUserId, HttpSession session) {
        int fromUserId = (Integer) session.getAttribute("userId");
        accountService.sendFriendRequest(fromUserId, toUserId);
        return "redirect:/friends";
    }

    @GetMapping("/friends")
    public ModelAndView showFriends(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("friends/friends");
        int userIdInSession = (Integer) session.getAttribute("userId");
        List<Account> friends = accountService.getFriends(userIdInSession);
        List<Account> requestedFriends = accountService.getRequestedFriends(userIdInSession);
        List<Account> requestFromFriends = accountService.getRequestFromFriends(userIdInSession);
        modelAndView.addObject("friends", friends);
        modelAndView.addObject("requestedFriends", requestedFriends);
        modelAndView.addObject("requestFromFriends", requestFromFriends);
        return modelAndView;
    }
}
