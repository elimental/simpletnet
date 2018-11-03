package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.web.util.WebUtils.makeUserName;

@Controller
@SessionAttributes("userId")
public class SearchController {

    private static final int PAGE_SIZE = 3;

    @Autowired
    private AccountService accountService;
    @Autowired
    private GroupService groupService;

    @GetMapping("/search")
    public ModelAndView search(@SessionAttribute("userId") int userIdInSession,
                               @RequestParam("search") String pattern) {
        ModelAndView modelAndView = new ModelAndView("searchResult");
        pattern = pattern.toLowerCase();
        int accountPageQty = getPageQty(accountSearch(pattern, userIdInSession));
        int groupPageQty = getPageQty(groupSearch(pattern));
        if (accountPageQty != 0) {
            List<SearchEntity> accountSearchEntities = getAccountSearchEntities(pattern, 1, userIdInSession);
            modelAndView.addObject("accountSearchEntities", accountSearchEntities);
            System.out.println(accountSearchEntities);
        }
        if (groupPageQty != 0) {
            List<SearchEntity> groupSearchEntities = getGroupSearchEntities(pattern, 1);
            modelAndView.addObject("groupSearchEntities", groupSearchEntities);
            System.out.println(groupSearchEntities);
        }
        modelAndView.addObject("pattern", pattern);
        modelAndView.addObject("accountPageQty", accountPageQty);
        modelAndView.addObject("groupPageQty", groupPageQty);


        return modelAndView;
    }

    @GetMapping("/searchPage")
    @ResponseBody
    public List<SearchEntity> getSearchPage(@SessionAttribute("userId") int userIdInSession,
                                            String pattern, String type, int pageNumber) {
        if (type.equals("account")) {
            return getAccountSearchEntities(pattern, pageNumber, userIdInSession);
        } else {
            return getGroupSearchEntities(pattern, pageNumber);
        }
    }

    private List<SearchEntity> getAccountSearchEntities(String pattern, int pageNumber, int exceptUserId) {
        List<Account> accounts = accountSearch(pattern, exceptUserId);
        List<SearchEntity> searchEntities = new ArrayList<>();
        for (Account account : accounts) {
            searchEntities.add(new SearchEntity(account.getId(), makeUserName(account)));
        }
        return getPage(searchEntities, pageNumber);
    }

    private List<SearchEntity> getGroupSearchEntities(String pattern, int pageNumber) {
        List<Group> groups = groupSearch(pattern);
        List<SearchEntity> searchEntities = new ArrayList<>();
        for (Group group : groups) {
            searchEntities.add(new SearchEntity(group.getId(), group.getName()));
        }
        return getPage(searchEntities, pageNumber);
    }

    private List<SearchEntity> getPage(List<SearchEntity> searchEntities, int pageNumber) {
        int indexFrom = (pageNumber - 1) * PAGE_SIZE;
        int indexTo = indexFrom + PAGE_SIZE;
        int lastListIndex = searchEntities.size();
        if (indexTo > lastListIndex) {
            indexTo = lastListIndex;
        }
        return searchEntities.subList(indexFrom, indexTo);
    }

    private int getPageQty(List list) {
        int pageQty = list.size() / PAGE_SIZE;
        int rest = list.size() % PAGE_SIZE;
        return rest == 0 ? pageQty : pageQty + 1;
    }

    private List<Account> accountSearch(String pattern, int exceptUserId) {
        List<Account> accounts = accountService.getAllUsers();
        List<Account> resultAccounts = new ArrayList<>();
        for (Account account : accounts) {
            String name = account.getFirstName() + account.getLastName();
            if (name.toLowerCase().contains(pattern)) {
                resultAccounts.add(account);
            }
        }
        resultAccounts.remove(accountService.getUserById(exceptUserId));
        return resultAccounts;
    }

    private List<Group> groupSearch(String pattern) {
        List<Group> groups = groupService.getAllGroups();
        List<Group> resultGroups = new ArrayList<>();
        for (Group group : groups) {
            String name = group.getName();
            if (name.toLowerCase().contains(pattern)) {
                resultGroups.add(group);
            }
        }
        return resultGroups;
    }

    public static class SearchEntity {
        private int id;
        private String name;

        public SearchEntity(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "SearchEntity{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
