package com.getjavajob.simplenet.dao.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.getjavajob.simplenet.common.entity.RelationStatus.ACCEPTED;
import static com.getjavajob.simplenet.common.entity.RelationStatus.PENDING;

@Repository
public class RelationshipDAO {

    private static final String SELECT_FRIENDS = "(SELECT userTwoId FROM relationship WHERE userOneId = ? " +
            "AND status = ?)" +
            "UNION " +
            "(SELECT userOneId FROM relationship WHERE userTwoId = ? AND status = ?)";
    private static final String SELECT_REQUESTED_FRIENDS = "(SELECT userTwoId FROM relationship WHERE userOneId = ? " +
            "AND status = ? AND lastActionUser = ?) " +
            "UNION " +
            "(SELECT userOneId FROM relationship WHERE userTwoId = ? AND status = ? AND lastActionUser = ?)";
    private static final String SELECT_REQUEST = "(SELECT userTwoId FROM relationship WHERE userOneId = ? " +
            "AND status = ? AND lastActionUser != ?) " +
            "UNION " +
            "(SELECT userOneId FROM relationship WHERE userTwoId = ? AND status = ? AND lastActionUser != ?)";
    private static final String INSERT_REQUEST = "INSERT INTO relationship (userOneId, userTwoId, status," +
            " lastActionUser) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ACCEPT_FRIEND = "UPDATE relationship SET status = ? WHERE userOneId = ? " +
            "AND userTwoId = ?";
    private static final String DELETE_USER = "DELETE FROM relationship WHERE userOneId = ? OR userTwoId = ?";
    private static final String DELETE_FRIEND = "DELETE FROM relationship WHERE (userOneId = ? " +
            "AND userTwoId = ?) OR (userTwoId = ? AND userOneId = ?)";
    private static final String SELECT_CHECK_REQUEST = "SELECT COUNT(*) FROM relationship WHERE userOneId = ? AND " +
            "userTwoId = ? AND status = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deleteUser(int id) {
        jdbcTemplate.update(DELETE_USER, id, id);
    }

    public List<Integer> getAllAccepted(int id) {
        return jdbcTemplate.query(SELECT_FRIENDS, new Object[]{id, ACCEPTED, id, ACCEPTED},
                (resultSet, i) -> resultSet.getInt(1));
    }

    public List<Integer> getAllRequested(int id) {
        return jdbcTemplate.query(SELECT_REQUESTED_FRIENDS, new Object[]{id, PENDING, id, id, PENDING, id},
                (resultSet, i) -> resultSet.getInt(1));
    }

    public List<Integer> getAllRequest(int id) {
        return jdbcTemplate.query(SELECT_REQUEST, new Object[]{id, PENDING, id, id, PENDING, id},
                (resultSet, i) -> resultSet.getInt(1));
    }

    public void sendFriendRequest(int fromUserId, int toUserId) {
        jdbcTemplate.update(INSERT_REQUEST, fromUserId, toUserId, PENDING, fromUserId);
    }

    public boolean checkRequestToOtherUser(int userFromId, int userToId) {
        return jdbcTemplate.queryForObject(SELECT_CHECK_REQUEST, new Object[]{userFromId, userToId, PENDING},
                Integer.class) == 1;
    }

    public void acceptFriend(int whoAcceptsId, int whoAcceptedId) {
        jdbcTemplate.update(UPDATE_ACCEPT_FRIEND, ACCEPTED, whoAcceptedId, whoAcceptsId);
    }

    public void deleteFriend(int whoDeclinesId, int whoDeclinedId) {
        jdbcTemplate.update(DELETE_FRIEND, whoDeclinedId, whoDeclinesId, whoDeclinedId, whoDeclinesId);
    }
}
