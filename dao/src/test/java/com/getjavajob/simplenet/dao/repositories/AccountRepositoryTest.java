package com.getjavajob.simplenet.dao.repositories;

import com.getjavajob.simplenet.common.entity.*;
import com.getjavajob.simplenet.dao.config.DAOConfig;
import com.getjavajob.simplenet.dao.config.DAOTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class AccountRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void populateDB() {

        // Accounts
        Account dima = new Account();   // id=1
        dima.setFirstName("Dima");
        dima.setEmail("elimental@bk.ru");
        dima.setPassword("qwerty");
        entityManager.persist(dima);

        Account vasya = new Account();   // id=2
        vasya.setFirstName("Vasya");
        vasya.setEmail("vasya@bk.ru");
        vasya.setPassword("qwerty");
        entityManager.persist(vasya);

        Account petya = new Account();   // id=3
        petya.setFirstName("Petya");
        petya.setEmail("petya@bk.ru");
        petya.setPassword("qwerty");
        entityManager.persist(petya);

        // Make Vasya and Petya friends
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFrom(vasya);
        friendRequest.setTo(petya);
        friendRequest.setAccepted(true);
        vasya.setRequestsToOtherAccount(new ArrayList<>());
        vasya.addFriendRequest(friendRequest);

        // Send friend request from Dima to Vasya
        FriendRequest friendRequest1 = new FriendRequest();
        friendRequest1.setFrom(dima);
        friendRequest1.setTo(vasya);
        dima.setRequestsToOtherAccount(new ArrayList<>());
        dima.addFriendRequest(friendRequest1);

        // Send message from Dima to Vasya
        PersonalMessage personalMessage = new PersonalMessage();
        personalMessage.setFrom(dima);
        personalMessage.setTo(vasya);
        personalMessage.setText("Hi");
        personalMessage.setCreateDate(new Date());
        dima.setFromAccount(new ArrayList<>());
        dima.addPersonalMessage(personalMessage);

        // Send message from Petya to Dima
        PersonalMessage personalMessage1 = new PersonalMessage();
        personalMessage1.setFrom(petya);
        personalMessage1.setTo(dima);
        personalMessage1.setText("Hi");
        personalMessage1.setCreateDate(new Date());
        petya.setFromAccount(new ArrayList<>());
        petya.addPersonalMessage(personalMessage1);

        // Dima creates own community
        Community community = new Community();
        community.setCreatorId(dima.getId());
        community.setName("Dima's community");
        CommunityRequest communityRequest = new CommunityRequest();
        communityRequest.setFrom(dima);
        communityRequest.setCommunity(community);
        communityRequest.setAccepted(true);
        community.getRequests().add(communityRequest);
        entityManager.persist(community);
    }

    @Test
    public void findByEmail() {
        Account expected = new Account();
        expected.setFirstName("Dima");
        expected.setEmail("elimental@bk.ru");
        expected.setPassword("qwerty");

        Account actual = accountRepository.findByEmail("unknown@mail.ru");
        assertNull(actual);
        actual = accountRepository.findByEmail("elimental@bk.ru");
        assertEquals(expected, actual);
    }

    @Test
    public void getFriendsFrom() {
        Account excepted = new Account();
        excepted.setEmail("petya@bk.ru");
        excepted.setPassword("qwerty");

        List<Account> friendsFrom = accountRepository.getFriendsFrom(2L);
        assertEquals(1, friendsFrom.size());
        Account actual = friendsFrom.get(0);
        assertEquals(excepted, actual);
    }

    @Test
    public void getFriendsTo() {
        Account excepted = new Account();
        excepted.setFirstName("Vasya");
        excepted.setEmail("vasya@bk.ru");

        List<Account> friendsTo = accountRepository.getFriendsTo(3L);
        assertEquals(1, friendsTo.size());
        Account actual = friendsTo.get(0);
        assertEquals(excepted, actual);
    }

    @Test
    public void getRequestedFriends() {
        Account excepted = new Account();
        excepted.setFirstName("Vasya");
        excepted.setEmail("vasya@bk.ru");

        List<Account> requestedFriends = accountRepository.getRequestedFriends(1L);
        assertEquals(1, requestedFriends.size());
        Account actual = requestedFriends.get(0);
        assertEquals(excepted, actual);
    }

    @Test
    public void getRequestFromFriends() {
        Account excepted = new Account();
        excepted.setFirstName("Dima");
        excepted.setEmail("elimental@bk.ru");

        List<Account> requestFromFriends = accountRepository.getRequestFromFriends(2L);
        assertEquals(1, requestFromFriends.size());
        Account actual = requestFromFriends.get(0);
        assertEquals(excepted, actual);
    }

    @Test
    public void getFriendship() {
        List<FriendRequest> friendship = accountRepository.getFriendship(2L, 3L, true);
        assertEquals(1, friendship.size());
        friendship = accountRepository.getFriendship(2L, 1L, true);
        assertEquals(0, friendship.size());
    }

    @Test
    public void getTalkersIdFrom() {
        Set<Long> excepted = new HashSet<>();
        excepted.add(3L);
        Set<Long> actual = accountRepository.getTalkersIdFrom(1L);
        assertEquals(excepted, actual);
    }

    @Test
    public void getTalkersIdTo() {
        Set<Long> excepted = new HashSet<>();
        excepted.add(2L);
        Set<Long> actual = accountRepository.getTalkersIdTo(1L);
        assertEquals(excepted, actual);
    }

    @Test
    public void deleteFromCommunity() {
        List<Community> communities = accountRepository.getCommunities(1L, true);
        assertEquals(1, communities.size());
        accountRepository.deleteFromCommunity(1L, 1L);
        communities = accountRepository.getCommunities(1L, true);
        assertEquals(0, communities.size());
    }

    @Test
    public void deleteFriend() {
        List<FriendRequest> friendship = accountRepository.getFriendship(2L, 3L, true);
        assertEquals(1, friendship.size());
        accountRepository.deleteFriend(2L, 3L);
        friendship = accountRepository.getFriendship(2L, 3L, true);
        assertEquals(0, friendship.size());
    }

    @Test
    public void acceptFriend() {
        List<FriendRequest> friendship = accountRepository.getFriendship(1L, 2L, true);
        assertEquals(0, friendship.size());
        accountRepository.acceptFriend(2L, 1L);
        friendship = accountRepository.getFriendship(1L, 2L, true);
        assertEquals(1, friendship.size());
    }

    @Test
    public void getPersonalMessages() {
        List<PersonalMessage> personalMessages = accountRepository.getPersonalMessages(1L, 2L);
        assertEquals(1, personalMessages.size());
        personalMessages = accountRepository.getPersonalMessages(2L, 3L);
        assertEquals(0, personalMessages.size());
    }

    @Test
    public void getCommunities() {
        Community excepted = new Community();
        excepted.setId(1L);
        excepted.setName("Dima's community");
        List<Community> communities = accountRepository.getCommunities(1L, true);
        assertEquals(1, communities.size());
        Community actual = communities.get(0);
        assertEquals(excepted, actual);
    }
}