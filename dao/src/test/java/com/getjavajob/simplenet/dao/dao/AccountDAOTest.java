package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Community;
import com.getjavajob.simplenet.common.entity.FriendRequest;
import com.getjavajob.simplenet.common.entity.PersonalMessage;
import com.getjavajob.simplenet.dao.config.DAOConfig;
import com.getjavajob.simplenet.dao.config.DAOTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
@Sql(value = {"classpath:ddl.sql", "classpath:populate_db.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"classpath:drop.sql"}, executionPhase = AFTER_TEST_METHOD)
public class AccountDAOTest {

    @Autowired
    private AccountDAO accountDAO;

    @Test
    public void getByEmail() {
        assertNull(accountDAO.getByEmail("unknown@mail.ru"));
        assertNotNull(accountDAO.getByEmail("elimental@bk.ru"));
    }

    @Test
    public void getFriendship() {
        FriendRequest actual = accountDAO.getFriendship(1L, 2L, true);
        assertEquals(1L, (long) actual.getFrom().getId());
        assertEquals(2L, (long) actual.getTo().getId());
        assertTrue(actual.getAccepted());
        assertNull(accountDAO.getFriendship(1L, 3L, true));
        assertNull(accountDAO.getFriendship(1L, 5L, true));
    }

    @Test
    public void getTalkersId() {
        Set<Long> except = new HashSet<>();
        except.add(2L);
        Set<Long> actual = accountDAO.getTalkersId(1L);
        assertEquals(except, actual);
    }

    @Test
    public void getCommunities() {
        List<Community> actual = accountDAO.getCommunities(1L, true);
        Community expected = actual.get(0);
        assertEquals((long) expected.getId(), 2L);
        assertEquals(expected.getName(), "group2");
        assertEquals((long) expected.getCreatorId(), 6L);
    }

    @Test
    @Transactional
    public void deleteFromCommunity() {
        assertFalse(accountDAO.getCommunities(1L, true).isEmpty());
        accountDAO.deleteFromCommunity(1L, 2L);
        assertTrue(accountDAO.getCommunities(1L, true).isEmpty());
    }

    @Test
    public void getFriends() {
        List<Account> expected = new ArrayList<>();
        Account friend1 = new Account();
        friend1.setId(2L);
        Account friend2 = new Account();
        friend2.setId(4L);
        expected.add(friend1);
        expected.add(friend2);
        List<Account> actual = accountDAO.getFriends(1L);
        assertEquals(expected, actual);

    }

    @Test
    public void getRequestedFriends() {
        List<Account> expected = new ArrayList<>();
        Account requestedFriend = new Account();
        requestedFriend.setId(3L);
        expected.add(requestedFriend);
        List<Account> actual = accountDAO.getRequestedFriends(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestFromFriends() {
        List<Account> expected = new ArrayList<>();
        Account requestFromFriend = new Account();
        requestFromFriend.setId(5L);
        expected.add(requestFromFriend);
        List<Account> actual = accountDAO.getRequestFromFriends(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void deleteFriend() {
        assertNotNull(accountDAO.getFriendship(1L, 2L, true));
        accountDAO.deleteFriend(1L, 2L);
        assertNull(accountDAO.getFriendship(1L, 2L, true));
    }

    @Test
    @Transactional
    public void acceptFriend() {
        assertNull(accountDAO.getFriendship(5L, 1L, true));
        accountDAO.acceptFriend(1L, 5L);
        assertNotNull(accountDAO.getFriendship(5L, 1L, true));
    }

    @Test
    public void getPersonalMessages() {
        List<PersonalMessage> expected = new ArrayList<>();
        PersonalMessage message1 = new PersonalMessage();
        message1.setId(1L);
        PersonalMessage message2 = new PersonalMessage();
        message2.setId(2L);
        expected.add(message1);
        expected.add(message2);
        List<PersonalMessage> actual = accountDAO.getPersonalMessages(1L, 2L);
        assertEquals(expected, actual);
    }
}