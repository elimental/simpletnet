package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.dao.config.DAOConfig;
import com.getjavajob.simplenet.dao.config.DAOTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
@Sql(value = {"classpath:drop_db.sql", "classpath:create_db.sql", "classpath:populate_db.sql"},
        executionPhase = BEFORE_TEST_METHOD)
public class RelationshipDAOTest {
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM relationship";
    private static final String SELECT_IF_FRIEND = "SELECT COUNT(*) FROM relationship WHERE userOneId = ? AND " +
            "userTwoId = ? AND status = ?";

    @Autowired
    private RelationshipDAO relationshipDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void deleteUser() {
        relationshipDAO.deleteUser(1);
        int count = jdbcTemplate.queryForObject(SELECT_COUNT, Integer.class);
        assertEquals(1, count);
    }

    @Test
    public void getAllAccepted() {
        List<Integer> expected = new ArrayList<>();
        expected.add(4);
        List<Integer> actual = relationshipDAO.getAllAccepted(1);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllRequested() {
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        List<Integer> actual = relationshipDAO.getAllRequested(1);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllRequest() {
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        List<Integer> actual = relationshipDAO.getAllRequest(2);
        assertEquals(expected, actual);
    }

    @Test
    public void sendFriendRequest() {
        relationshipDAO.sendFriendRequest(1, 3);
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        List<Integer> actual = relationshipDAO.getAllRequested(1);
        assertEquals(expected, actual);
    }

    @Test
    public void checkRequestToOtherUser() {
        assertTrue(relationshipDAO.checkRequestToOtherUser(1, 2));
        assertFalse(relationshipDAO.checkRequestToOtherUser(1, 3));
    }

    @Test
    public void acceptFriend() {
        relationshipDAO.acceptFriend(2, 1);
        int count = jdbcTemplate.queryForObject(SELECT_IF_FRIEND, Integer.class, 1, 2, 2);
        assertEquals(1, count);

    }

    @Test
    public void deleteFriend() {
        relationshipDAO.deleteFriend(2, 3);
        int count = jdbcTemplate.queryForObject(SELECT_IF_FRIEND, Integer.class, 2, 3, 2);
        assertEquals(0, count);
    }
}