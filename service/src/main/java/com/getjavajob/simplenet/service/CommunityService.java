package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.*;
import com.getjavajob.simplenet.dao.dao.AccountDAO;
import com.getjavajob.simplenet.dao.dao.CommunityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.*;
import static java.lang.System.currentTimeMillis;

@Service
@Transactional
public class CommunityService {

    private final static boolean ACCEPTED_REQUEST = true;
    private final static boolean NOT_ACCEPTED_REQUEST = false;

    private final AccountDAO accountDAO;
    private final CommunityDAO communityDAO;

    @Autowired
    public CommunityService(AccountDAO accountDAO, CommunityDAO communityDAO) {
        this.accountDAO = accountDAO;
        this.communityDAO = communityDAO;
    }

    public List<Community> getAccountCommunities(Long accountId) {
        return accountDAO.getCommunities(accountId, ACCEPTED_REQUEST);
    }

    public List<Community> getAllCommunities() {
        return communityDAO.getAll();
    }

    public List<Community> getRequestedCommunities(long accountId) {
        return accountDAO.getCommunities(accountId, NOT_ACCEPTED_REQUEST);
    }

    public void addCommunity(Community community, long creatorId) {
        Account creator = accountDAO.get(creatorId);
        community.setCreateDate(new Date(currentTimeMillis()));
        community.setCreatorId(creator.getId());
        CommunityRequest communityRequest = new CommunityRequest();
        communityRequest.setFrom(creator);
        communityRequest.setCommunity(community);
        communityRequest.setAccepted(ACCEPTED_REQUEST);
        communityRequest.setRole(Role.GROUP_MODERATOR);
        community.getRequests().add(communityRequest);
        communityDAO.add(community);
    }

    public boolean ifCommunityMember(long accountId, long communityId) {
        return communityDAO.getRequest(accountId, communityId, ACCEPTED_REQUEST) != null;
    }

    public boolean ifAlreadyRequested(long accountId, long communityId) {
        return communityDAO.getRequest(accountId, communityId, NOT_ACCEPTED_REQUEST) != null;
    }

    public Community getCommunityById(long communityId) {
        return communityDAO.get(communityId);
    }

    public boolean ifCommunityCreator(long accountId, long communityId) {
        Long creatorId = accountDAO.get(accountId).getId();
        return communityDAO.get(communityId).getCreatorId().equals(creatorId);
    }

    public boolean ifCommunityModerator(long accountId, long communityId) {
        return communityDAO.checkCommunityRole(accountId, communityId, GROUP_MODERATOR) != null;
    }

    public void updateCommunity(Community community) {
        communityDAO.get(community.getId()).updateCommunity(community);
    }

    //
//    public Community getGroupById(int groupId) {
//        return groupDAO.getById(groupId);
//    }
//
//    @Transactional
//    public void sendGroupRequest(int userId, int groupId) {
//        accountGroupDAO.sendGroupRequest(userId, groupId);
//    }
//
//    @Transactional
//    public void acceptGroupRequest(int userId, int groupId) {
//        accountGroupDAO.acceptGroupRequest(userId, groupId);
//    }
//
//
//    @Transactional
//    public void rejectGroupRequest(int userId, int groupId) {
//        accountGroupDAO.rejectGroupRequest(userId, groupId);
//    }
//
//    public List<Account> getCandidates(int groupId) {
//        List<Integer> candidatesIds = accountGroupDAO.getCandidatesIds(groupId);
//        List<Account> candidates = new ArrayList<>();
//        for (Integer i : candidatesIds) {
//            candidates.add(accountDAO.getById(i));
//        }
//        return candidates;
//    }
//
//    public boolean ifGroupOwner(int userId, int groupId) {
//        Community community = groupDAO.getById(groupId);
//        return community.getOwner() == userId;
//    }
//
//    public boolean ifGroupMember(int userId, int grouId) {
//        List<Integer> userGroupsId = getUserGroupsId(userId);
//        return userGroupsId.contains(grouId);
//    }
//
//    public boolean ifGroupModerator(int userId, int groupId) {
//        int roleId = accountGroupDAO.getUserRoleInGroup(userId, groupId);
//        if (roleId == 0) {
//            return false;
//        }
//        return roleId == GROUP_MODERATOR;
//    }
//
//    public List<Community> getAllGroups() {
//        return groupDAO.getAll();
//    }
//
//    @Transactional
//    public void deleteGroup(int id) {
//        groupDAO.delete(id);
//        accountGroupDAO.deleteGroup(id);
//    }
//
//    public List<Account> getRoleMembers(int groupId) {
//        List<Integer> userIds = accountGroupDAO.getMembersId(groupId);
//        List<Account> members = new ArrayList<>();
//        for (Integer i : userIds) {
//            members.add(accountDAO.getById(i));
//        }
//        return members;
//    }
//
//    public boolean ifAlreadyRequested(int userId, int groupId) {
//        List<Integer> candidates = accountGroupDAO.getCandidatesIds(groupId);
//        return candidates.contains(userId);
//    }
//
//    @Transactional
//    public void makeModerator(int userId, int groupId) {
//        accountGroupDAO.makeModerator(userId, groupId);
//    }
//
//    @Transactional
//    public void deleteFromGroup(int userId, int groupId) {
//        accountGroupDAO.deleteFromGroup(userId, groupId);
//    }
//
//    public List<Community> getRequestedGroups(int userId) {
//        List<Integer> requestedGroupsId = getRequestedGroupsId(userId);
//        List<Community> groups = new ArrayList<>();
//        for (Integer i : requestedGroupsId) {
//            groups.add(groupDAO.getById(i));
//        }
//        return groups;
//    }
//
//    private List<Integer> getRequestedGroupsId(int userId) {
//        return accountGroupDAO.getRequestedGroupsId(userId);
//    }
    public byte[] getImage(Long id) {
        Community community = communityDAO.get(id);
        return community == null ? null : community.getPicture();
    }

    public void deleteCommunity(long id) {
        communityDAO.deleteById(id);
    }

    public List<CommunityMessage> getCommunityMessages(long communityId) {
        List<CommunityMessage> communityMessages = communityDAO.get(communityId).getMessages();
        communityMessages.size();
        Collections.sort(communityMessages);
        return communityMessages;
    }

    public void addCommunityMessage(long communityId, long authorId, String message) {
        communityDAO.get(communityId).addCommunityMessage(authorId, message);
    }

    public void deleteCommunityMessage(long messageId, long communityId) {
        communityDAO.get(communityId).removeCommunityMessage(messageId);
    }

    public List<Account> getMembers(long communityId) {
        return communityDAO.getRoleMembers(communityId, GROUP_MEMBER);
    }

    public List<Account> getModerators(long communityId) {
        return communityDAO.getRoleMembers(communityId, GROUP_MODERATOR);
    }

    public List<Account> getCandidates(long communityId) {
        return communityDAO.getRoleMembers(communityId, GROUP_CANDIDATE);
    }

    public void sendCommunityRequest(long accountId, long communityId) {
        communityDAO.get(communityId).addRequest(accountDAO.get(accountId));
    }

    public void rejectCommunityRequest(long accountId, long communityId) {
        accountDAO.deleteFromCommunity(accountId, communityId);
    }

    public void acceptCommunityRequest(long accountId, long communityId) {
        communityDAO.acceptCommunityRequest(accountId, communityId);
    }

    public void makeModerator(long accountId, long communityId) {
        communityDAO.makeModerator(accountId, communityId);
    }
}
