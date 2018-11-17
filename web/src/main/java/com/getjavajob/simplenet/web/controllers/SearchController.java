package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("userId")
public class SearchController {

    private static final int PAGE_SIZE = 3;

    private final AccountService accountService;
    private final CommunityService communityService;

    @Autowired
    public SearchController(AccountService accountService, CommunityService communityService) {
        this.accountService = accountService;
        this.communityService = communityService;
    }

    @GetMapping("/search")
    public ModelAndView search(@SessionAttribute("userId") int userIdInSession,
                               @RequestParam("search") String pattern) {
        ModelAndView modelAndView = new ModelAndView("searchResult");
        pattern = pattern.toLowerCase();
        int accountPageQty = getPageQty(accountSearch(pattern, userIdInSession));
        int communityPageQty = getPageQty(communitySearch(pattern));
        if (accountPageQty != 0) {
            List<SearchEntity> accountSearchEntities = getAccountSearchEntities(pattern, 1, userIdInSession);
            modelAndView.addObject("accountSearchEntities", accountSearchEntities);
        }
        if (communityPageQty != 0) {
            List<SearchEntity> communitySearchEntities = getCommunitySearchEntities(pattern, 1);
            modelAndView.addObject("communitySearchEntities", communitySearchEntities);
        }
        modelAndView.addObject("pattern", pattern);
        modelAndView.addObject("accountPageQty", accountPageQty);
        modelAndView.addObject("communityPageQty", communityPageQty);
        return modelAndView;
    }

    @GetMapping("/searchPage")
    @ResponseBody
    public List<SearchEntity> getSearchPage(@SessionAttribute("userId") long userIdInSession,
                                            String pattern, String type, int pageNumber) {
        if (type.equals("account")) {
            return getAccountSearchEntities(pattern, pageNumber, userIdInSession);
        } else {
            return getCommunitySearchEntities(pattern, pageNumber);
        }
    }

    private List<SearchEntity> getAccountSearchEntities(String pattern, int pageNumber, long exceptUserId) {
        List<Account> accounts = accountSearch(pattern, exceptUserId);
        List<SearchEntity> searchEntities = new ArrayList<>();
        for (Account account : accounts) {
            searchEntities.add(new SearchEntity(account.getId(), account.getAccountFullName()));
        }
        return getPage(searchEntities, pageNumber);
    }

    private List<SearchEntity> getCommunitySearchEntities(String pattern, int pageNumber) {
        List<Community> communities = communitySearch(pattern);
        List<SearchEntity> searchEntities = new ArrayList<>();
        for (Community community : communities) {
            searchEntities.add(new SearchEntity(community.getId(), community.getName()));
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

    private List<Account> accountSearch(String pattern, long exceptUserId) {
        List<Account> accounts = accountService.getAllAccounts();
        List<Account> resultAccounts = new ArrayList<>();
        for (Account account : accounts) {
            String name = account.getFirstName() + account.getLastName();
            if (name.toLowerCase().contains(pattern)) {
                resultAccounts.add(account);
            }
        }
        resultAccounts.remove(accountService.getAccountById(exceptUserId));
        return resultAccounts;
    }

    private List<Community> communitySearch(String pattern) {
        List<Community> communities = communityService.getAllCommunities();
        List<Community> resultCommunities = new ArrayList<>();
        for (Community community : communities) {
            String name = community.getName();
            if (name.toLowerCase().contains(pattern)) {
                resultCommunities.add(community);
            }
        }
        return resultCommunities;
    }

    public static class SearchEntity {
        private long id;
        private String name;

        SearchEntity(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
