package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private GroupService groupService;

    @GetMapping("/search")
    public ModelAndView search(String search, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("searchResult");
        search = search.toLowerCase();
        List<Account> accounts = accountService.getAllUsers();
        List<Group> groups = groupService.getAllGroups();
        List<Account> resultAccounts = new ArrayList<>();
        for (Account account : accounts) {
            String name = account.getFirstName() + account.getLastName();
            if (name.toLowerCase().contains(search)) {
                resultAccounts.add(account);
            }
        }
        List<Group> resultGroups = new ArrayList<>();
        for (Group group : groups) {
            String name = group.getName();
            if (name.toLowerCase().contains(search)) {
                resultGroups.add(group);
            }
        }
        int userIdInSession = (Integer) session.getAttribute("userId");
        Account account = accountService.getUserById(userIdInSession);
        resultAccounts.remove(account);
        modelAndView.addObject("accounts", resultAccounts);
        modelAndView.addObject("groups", resultGroups);
        return modelAndView;
    }
}
