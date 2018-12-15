package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.*;
import com.getjavajob.simplenet.dao.dao.CommunityDAO;
import com.getjavajob.simplenet.dao.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final CommunityDAO communityDAO;
    private final AccountRepository accountRepository;

    @Autowired
    public CommunityService(CommunityDAO communityDAO, AccountRepository accountRepository) {
        this.communityDAO = communityDAO;
        this.accountRepository = accountRepository;
    }

    public List<Community> getAccountCommunities(Long accountId) {
        return accountRepository.getCommunities(accountId, ACCEPTED_REQUEST);
    }

    public List<Community> getAllCommunities() {
        return communityDAO.getAll();
    }

    public List<Community> getRequestedCommunities(long accountId) {
        return accountRepository.getCommunities(accountId, NOT_ACCEPTED_REQUEST);
    }

    public void addCommunity(Community community, long creatorId) {
        Account creator = accountRepository.findById(creatorId).get();
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
        Long creatorId = accountRepository.findById(accountId).get().getId();
        return communityDAO.get(communityId).getCreatorId().equals(creatorId);
    }

    public boolean ifCommunityModerator(long accountId, long communityId) {
        return communityDAO.checkCommunityRole(accountId, communityId, GROUP_MODERATOR) != null;
    }

    public void updateCommunity(Community community) {
        communityDAO.get(community.getId()).updateCommunity(community);
    }

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
        communityDAO.get(communityId).addRequest(accountRepository.findById(accountId).get());
    }

    public void rejectCommunityRequest(long accountId, long communityId) {
        accountRepository.deleteFromCommunity(accountId, communityId);
    }

    public void acceptCommunityRequest(long accountId, long communityId) {
        communityDAO.acceptCommunityRequest(accountId, communityId);
    }

    public void makeModerator(long accountId, long communityId) {
        communityDAO.makeModerator(accountId, communityId);
    }
}
