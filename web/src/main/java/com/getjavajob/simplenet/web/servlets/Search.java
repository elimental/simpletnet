package com.getjavajob.simplenet.web.servlets;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class Search extends HttpServlet {
    private AccountService accountService = new AccountService();
    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search").toLowerCase();
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
        int id = (Integer) req.getSession().getAttribute("userId");
        Account account = accountService.getUserById(id);
        resultAccounts.remove(account);
        req.setAttribute("accounts", resultAccounts);
        req.setAttribute("groups", resultGroups);
        req.getRequestDispatcher("/jsp/searchresult.jsp").forward(req, resp);
    }
}
