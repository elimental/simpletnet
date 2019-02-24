package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.dao.dao.CommunityDAO;
import com.getjavajob.simplenet.dao.repositories.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static com.getjavajob.simplenet.common.entity.Role.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommunityServiceTest {

    @Mock
    CommunityDAO communityDAO;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    CommunityService communityService;

    @Test
    public void getAccountCommunities() {
        communityService.getAccountCommunities(1L);
        verify(accountRepository).getCommunities(1L, true);
    }

    @Test
    public void getAllCommunities() {
        communityService.getAllCommunities();
        verify(communityDAO).getAll();
    }

    @Test
    public void getRequestedCommunities() {
        communityService.getRequestedCommunities(1L);
        verify(accountRepository).getCommunities(1L, false);
    }

    @Test
    public void addCommunity() {
        Account account = new Account();
        account.setId(1L);
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        Community community = new Community();
        communityService.addCommunity(community, 1L);
        verify(accountRepository).findById(1L);
        verify(communityDAO).add(community);
    }

    @Test
    public void ifCommunityMember() {
        communityService.ifCommunityMember(1L, 2L);
        verify(communityDAO).getRequest(1L, 2L, true);
    }

    @Test
    public void ifAlreadyRequested() {
        communityService.ifAlreadyRequested(1L, 2L);
        verify(communityDAO).getRequest(1L, 2L, false);
    }

    @Test
    public void getCommunityById() {
        communityService.getCommunityById(1L);
        verify(communityDAO).get(1L);
    }

    @Test
    public void ifCommunityCreator() {
        Account account = new Account();
        account.setId(1L);
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);

        Community community = new Community();
        community.setCreatorId(1L);
        when(communityDAO.get(2L)).thenReturn(community);

        communityService.ifCommunityCreator(1L, 2L);
        verify(accountRepository).findById(1L);
        verify(communityDAO).get(2L);
    }

    @Test
    public void ifCommunityModerator() {
        communityService.ifCommunityModerator(1L, 2L);
        verify(communityDAO).checkCommunityRole(1L, 2L, GROUP_MODERATOR);
    }

    @Test
    public void updateCommunity() {
        Community community = new Community();
        community.setId(1L);
        when(communityDAO.get(1L)).thenReturn(community);
        communityService.updateCommunity(community);
        verify(communityDAO).get(1L);
    }

    @Test
    public void getImage() {
        communityService.getImage(1L);
        verify(communityDAO).get(1L);
    }

    @Test
    public void deleteCommunity() {
        communityService.deleteCommunity(1L);
        verify(communityDAO).deleteById(1L);
    }

    @Test
    public void getCommunityMessages() {
        Community community = new Community();
        community.setMessages(new ArrayList<>());
        when(communityDAO.get(1L)).thenReturn(community);
        communityService.getCommunityMessages(1L);
        verify(communityDAO).get(1L);
    }

    @Test
    public void addCommunityMessage() {
        Community community = new Community();
        community.setMessages(new ArrayList<>());
        when(communityDAO.get(2L)).thenReturn(community);
        communityService.addCommunityMessage(2L, 1L, "text");
        verify(communityDAO).get(2L);
    }

    @Test
    public void deleteCommunityMessage() {
        Community community = new Community();
        community.setMessages(new ArrayList<>());
        when(communityDAO.get(2L)).thenReturn(community);
        communityService.deleteCommunityMessage(1L, 2L);
        verify(communityDAO).get(2L);
    }

    @Test
    public void getMembers() {
        communityService.getMembers(1L);
        verify(communityDAO).getRoleMembers(1L, GROUP_MEMBER);
    }

    @Test
    public void getModerators() {
        communityService.getModerators(1L);
        verify(communityDAO).getRoleMembers(1L, GROUP_MODERATOR);
    }

    @Test
    public void getCandidates() {
        communityService.getCandidates(1L);
        verify(communityDAO).getRoleMembers(1L, GROUP_CANDIDATE);
    }

    @Test
    public void sendCommunityRequest() {
        Community community = new Community();
        community.setMessages(new ArrayList<>());
        when(communityDAO.get(2L)).thenReturn(community);

        Account account = new Account();
        account.setId(1L);
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);

        communityService.sendCommunityRequest(1L, 2L);
        verify(accountRepository).findById(1L);
        verify(communityDAO).get(2L);
    }

    @Test
    public void rejectCommunityRequest() {
        communityService.rejectCommunityRequest(1L, 2L);
        verify(accountRepository).deleteFromCommunity(1L, 2L);
    }

    @Test
    public void acceptCommunityRequest() {
        communityService.acceptCommunityRequest(1L, 2L);
        verify(communityDAO).acceptCommunityRequest(1L, 2L);
    }

    @Test
    public void makeModerator() {
        communityService.makeModerator(1L,2L);
        verify(communityDAO).makeModerator(1L, 2L);
    }
}