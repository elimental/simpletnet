package com.getjavajob.simplenet.dao.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.*;

@Repository
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Integer> getUserGroupsId(int userId) {
        return jdbcTemplate.query(SELECT_USER_GROUPS_ID, new Object[]{userId, GROUP_MEMBER, GROUP_MODERATOR},
                (resultSet, i) -> resultSet.getInt("groupId"));
    }

    public void addGroup(int ownerId, int groupId) {
        jdbcTemplate.update(INSERT_ADD_GROUP, ownerId, groupId, GROUP_MODERATOR);
    }

    public void sendGroupRequest(int userId, int groupId) {
        jdbcTemplate.update(INSERT_REQUEST, userId, groupId, GROUP_CANDIDATE);
    }

    public void acceptGroupRequest(int userId, int groupId) {
        jdbcTemplate.update(UPDATE_ACCEPT_REQUEST, GROUP_MEMBER, userId, groupId);
    }

    public void rejectGroupRequest(int userId, int groupId) {
        jdbcTemplate.update(DELETE_REJECT_REQUEST, userId, groupId);
    }

    public int getUserRoleInGroup(int userId, int groupId) {
        List<Integer> roleId = jdbcTemplate.query(SELECT_USER_ROLE_IN_GROUP, new Object[]{userId, groupId},
                (resultSet, i) -> resultSet.getInt("userRole"));
        return roleId.isEmpty() ? 0 : roleId.get(0);
    }

    public void deleteUser(int id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    public void deleteGroup(int id) {
        jdbcTemplate.update(DELETE_GROUP, id);
    }

    public List<Integer> getCandidatesIds(int groupId) {
        return jdbcTemplate.query(SELECT_CANDIDATES_ID, new Object[]{groupId, GROUP_CANDIDATE},
                (resultSet, i) -> resultSet.getInt("userId"));
    }

    public List<Integer> getMemberIds(int groupId) {
        return jdbcTemplate.query(SELECT_MEMBERS_ID, new Object[]{groupId, GROUP_MEMBER, GROUP_MODERATOR},
                (resultSet, i) -> resultSet.getInt("userId"));
    }

    public void makeModerator(int userId, int groupId) {
        jdbcTemplate.update(UPDATE_MAKE_MODERATOR, GROUP_MODERATOR, userId, groupId);
    }

    public void deleteFromGroup(int userId, int groupId) {
        jdbcTemplate.update(DELETE_FROM_GROUP, userId, groupId);
    }
}
