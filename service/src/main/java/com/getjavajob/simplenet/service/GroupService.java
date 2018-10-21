package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.dao.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.GROUP_MODERATOR;

@Service
public class GroupService {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private AccountGroupDAO accountGroupDAO;
    @Autowired
    private GroupDAO groupDAO;

    public List<Group> getUserGroups(int userId) {
        List<Integer> groupsId = getUserGroupsId(userId);
        List<Group> groups = new ArrayList<>();
        for (Integer i : groupsId) {
            groups.add(groupDAO.getById(i));
        }
        return groups;
    }

    private List<Integer> getUserGroupsId(int userId) {
        return accountGroupDAO.getUserGroupsId(userId);
    }

    @Transactional
    public void addGroup(Group group) {
        int id = groupDAO.add(group);
        accountGroupDAO.addGroup(group.getOwner(), id);
    }

    @Transactional
    public void updateGroup(Group group) {
        groupDAO.update(group);
    }

    public Group getGroupById(int groupId) {
        return groupDAO.getById(groupId);
    }

    @Transactional
    public void sendGroupRequest(int userId, int groupId) {
        accountGroupDAO.sendGroupRequest(userId, groupId);
    }

    @Transactional
    public void acceptGroupRequest(int userId, int groupId) {
        accountGroupDAO.acceptGroupRequest(userId, groupId);
    }


    @Transactional
    public void rejectGroupRequest(int userId, int groupId) {
        accountGroupDAO.rejectGroupRequest(userId, groupId);
    }

    public List<Account> getCandidates(int groupId) {
        List<Integer> candidatesIds = accountGroupDAO.getCandidatesIds(groupId);
        List<Account> candidates = new ArrayList<>();
        for (Integer i : candidatesIds) {
            candidates.add(accountDAO.getById(i));
        }
        return candidates;
    }

    public boolean ifGroupOwner(int userId, int groupId) {
        Group group = groupDAO.getById(groupId);
        return group.getOwner() == userId;
    }

    public boolean ifGroupMember(int userId, int grouId) {
        List<Integer> userGroupsId = getUserGroupsId(userId);
        return userGroupsId.contains(grouId);
    }

    public boolean ifGroupModerator(int userId, int groupId) {
        int roleId = accountGroupDAO.getUserRoleInGroup(userId, groupId);
        if (roleId == 0) {
            return false;
        }
        return roleId == GROUP_MODERATOR;
    }

    public List<Group> getAllGroups() {
        return groupDAO.getAll();
    }

    @Transactional
    public void deleteGroup(int id) {
        groupDAO.delete(id);
        accountGroupDAO.deleteGroup(id);
    }

    public List<Account> getMembers(int groupId) {
        List<Integer> userIds = accountGroupDAO.getMembersId(groupId);
        List<Account> members = new ArrayList<>();
        for (Integer i : userIds) {
            members.add(accountDAO.getById(i));
        }
        return members;
    }

    public boolean ifAlreadyRequested(int userId, int groupId) {
        List<Integer> candidates = accountGroupDAO.getCandidatesIds(groupId);
        return candidates.contains(userId);
    }

    @Transactional
    public void makeModerator(int userId, int groupId) {
        accountGroupDAO.makeModerator(userId, groupId);
    }

    @Transactional
    public void deleteFromGroup(int userId, int groupId) {
        accountGroupDAO.deleteFromGroup(userId, groupId);
    }
}
