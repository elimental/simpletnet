package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountGroupDAO {
    private static final String SELECT_USER_GROUPS_ID = "SELECT groupId FROM account_groups WHERE userId = ?";
    private static final String INSERT_USER_GROUP = "INSERT INTO account_groups (userId, groupId, userRole) " +
            "VALUES (?, ?, ?)";
    private static final String SELECT_USER_ROLE_IN_GROUP = "SELECT userRole FROM account_groups WHERE userId = ? " +
            "AND groupId = ?";
    private static final String DELETE_USER = "DELETE FROM account_groups WHERE userId = ?";
    private static final String DELETE_GROUP = "DELETE FROM account_groups WHERE groupId = ?";
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    public List<Integer> getUserGroupsId(int userId) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_GROUPS_ID);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            List<Integer> groupsId = new ArrayList<>();
            while (rs.next()) {
                groupsId.add(rs.getInt("groupId"));
            }
            return groupsId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUserGroup(int userId, int groupId, int roleId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_USER_GROUP);
        ps.setInt(1, userId);
        ps.setInt(2, groupId);
        ps.setInt(3, roleId);
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
}
