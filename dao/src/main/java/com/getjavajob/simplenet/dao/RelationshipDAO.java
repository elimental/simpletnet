package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.RelationStatus.ACCEPTED;
import static com.getjavajob.simplenet.common.entity.RelationStatus.PENDING;

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
    private static final String SELECT_CHECK_REQUEST = "SELECT * FROM relationship WHERE userOneId = ? AND " +
            "userTwoId = ? AND status = ?";

    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    public void deleteUser(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_USER);
        ps.setInt(1, id);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public List<Integer> getAllAccepted(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_FRIENDS);
            ps.setInt(1, id);
            ps.setInt(2, ACCEPTED);
            ps.setInt(3, id);
            ps.setInt(4, ACCEPTED);
            List<Integer> friends = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                friends.add(rs.getInt(1));
            }
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getAllRequested(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_REQUESTED_FRIENDS);
            return getIds(id, ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getAllRequest(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_REQUEST);
            return getIds(id, ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Integer> getIds(int id, PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
        ps.setInt(2, PENDING);
        ps.setInt(3, id);
        ps.setInt(4, id);
        ps.setInt(5, PENDING);
        ps.setInt(6, id);
        List<Integer> requested = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            requested.add(rs.getInt(1));
        }
        return requested;
    }

    public void sendFriendRequest(int fromUserId, int toUserId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_REQUEST);
        ps.setInt(1, fromUserId);
        ps.setInt(2, toUserId);
        ps.setInt(3, PENDING);
        ps.setInt(4, fromUserId);
        ps.executeUpdate();
    }

    public boolean checkRequestToOtherUser(int userFromId, int userToId) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_CHECK_REQUEST);
            ps.setInt(1,userFromId);
            ps.setInt(2,userToId);
            ps.setInt(3,PENDING);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void acceptFriend(int whoAcceptsId, int whoAcceptedId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE_ACCEPT_FRIEND);
        ps.setInt(1, ACCEPTED);
        ps.setInt(2, whoAcceptedId);
        ps.setInt(3, whoAcceptsId);
        ps.executeUpdate();
    }

    public void deleteFriend(int whoDeclinesId, int whoDeclinedId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_FRIEND);
        ps.setInt(1, whoDeclinedId);
        ps.setInt(2, whoDeclinesId);
        ps.setInt(3, whoDeclinedId);
        ps.setInt(4, whoDeclinesId);
        ps.executeUpdate();
    }
}
