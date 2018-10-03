package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.*;

public class AccountGroupDAO {
    private static final String SELECT_USER_GROUPS_ID = "SELECT groupId FROM account_groups WHERE userId = ? " +
            "AND (userRole = ? OR userRole = ?)";
    private static final String INSERT_ADD_GROUP = "INSERT INTO account_groups (userId, groupId, userRole) " +
            "VALUES (?, ?, ?)";
    private static final String INSERT_REQUEST = "INSERT INTO account_groups (userId, groupId, userRole) " +
            "VALUES (?, ?, ?)";
    private static final String UPDATE_ACCEPT_REQUEST = "UPDATE account_groups SET userRole = ? WHERE userId = ? " +
            "AND groupId = ?";
    private static final String SELECT_USER_ROLE_IN_GROUP = "SELECT userRole FROM account_groups WHERE userId = ? " +
            "AND groupId = ?";
    private static final String DELETE_REJECT_REQUEST = "DELETE FROM account_groups WHERE userId = ? AND groupId = ?";
    private static final String SELECT_CANDIDATES_ID = "SELECT userId FROM account_groups WHERE groupId = ? AND " +
            "userRole = ?";
    private static final String SELECT_MEMBERS_ID = "SELECT userId FROM account_groups WHERE groupId = ? AND " +
            "userRole = ? OR userRole = ?";
    private static final String UPDATE_MAKE_MODERATOR = "UPDATE account_groups SET userRole = ? WHERE userId = ? " +
            "AND groupId = ?";
    private static final String DELETE_FROM_GROUP = "DELETE FROM account_groups WHERE userId = ? AND groupId = ?";
    private static final String DELETE_USER = "DELETE FROM account_groups WHERE userId = ?";
    private static final String DELETE_GROUP = "DELETE FROM account_groups WHERE groupId = ?";

    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    public List<Integer> getUserGroupsId(int userId) {
        List<Integer> groupsId = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_GROUPS_ID);
            ps.setInt(1, userId);
            ps.setInt(2, GROUP_MEMBER);
            ps.setInt(3, GROUP_MODERATOR);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                groupsId.add(rs.getInt("groupId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupsId;
    }

    public void addGroup(int ownerId, int groupId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_ADD_GROUP);
        ps.setInt(1, ownerId);
        ps.setInt(2, groupId);
        ps.setInt(3, GROUP_MODERATOR);
        ps.executeUpdate();
    }

    public void sendGroupRequest(int userId, int groupId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_REQUEST);
        ps.setInt(1, userId);
        ps.setInt(2, groupId);
        ps.setInt(3, GROUP_CANDIDATE);
        ps.executeUpdate();
    }

    public void acceptGroupRequest(int userId, int groupId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE_ACCEPT_REQUEST);
        ps.setInt(1, GROUP_MEMBER);
        ps.setInt(2, userId);
        ps.setInt(3, groupId);
        ps.executeUpdate();
    }

    public void rejectGroupRequest(int userId, int groupId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_REJECT_REQUEST);
        ps.setInt(1, userId);
        ps.setInt(2, groupId);
        ps.executeUpdate();
    }


    public int getUserRoleInGroup(int userId, int groupId) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_ROLE_IN_GROUP);
            ps.setInt(1, userId);
            ps.setInt(2, groupId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("userRole");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteUser(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_USER);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void deleteGroup(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_GROUP);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public List<Integer> getCandidatesIds(int groupId) {
        List<Integer> candidatesIds = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_CANDIDATES_ID);
            ps.setInt(1, groupId);
            ps.setInt(2, GROUP_CANDIDATE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                candidatesIds.add(rs.getInt("userId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidatesIds;
    }

    public List<Integer> getMemberIds(int groupId) {
        List<Integer> memberIds = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_MEMBERS_ID);
            ps.setInt(1, groupId);
            ps.setInt(2, GROUP_MEMBER);
            ps.setInt(3, GROUP_MODERATOR);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                memberIds.add(rs.getInt("userId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberIds;
    }

    public void makeModerator(int userId, int groupId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE_MAKE_MODERATOR);
        ps.setInt(1, GROUP_MODERATOR);
        ps.setInt(2, userId);
        ps.setInt(3, groupId);
        ps.executeUpdate();
    }

    public void deleteFromGroup(int userId, int groupId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_FROM_GROUP);
        ps.setInt(1, userId);
        ps.setInt(2, groupId);
        ps.executeUpdate();
    }
}
