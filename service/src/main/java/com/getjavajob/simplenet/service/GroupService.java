package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.dao.AccountGroupDAO;
import com.getjavajob.simplenet.dao.GroupDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.MODERATOR;
import static com.getjavajob.simplenet.common.entity.Role.USER;

public class GroupService {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    public List<Group> getUserGroups(int userId) {
        List<Integer> groupsId = getUserGroupsId(userId);
        GroupDAO groupDAO = new GroupDAO();
        List<Group> groups = new ArrayList<>();
        for (Integer i : groupsId) {
            groups.add(groupDAO.getById(i));
        }
        return groups;
    }

    private List<Integer> getUserGroupsId(int userId) {
        AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
        return accountGroupDAO.getUserGroupsId(userId);
    }

    public boolean ifGroupMember(int userId, int grouId) {
        List<Integer> userGroupsId = getUserGroupsId(userId);
        return userGroupsId.contains(grouId);
    }

    public void addGroup(Group group) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            GroupDAO groupDAO = new GroupDAO();
            int id = groupDAO.add(group);
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.addUserGroup(group.getOwner(), id, MODERATOR);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateGroup(Group group) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            GroupDAO groupDAO = new GroupDAO();
            groupDAO.update(group);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Group getGroupById(int groupId) {
        GroupDAO groupDAO = new GroupDAO();
        return groupDAO.getById(groupId);
    }

    public void addUserGroup(Group group, int userId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.addUserGroup(userId, group.getId(), USER);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean ifGroupOwner(int userId, int groupId) {
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.getById(groupId);
        return group.getOwner() == userId;
    }

    public boolean ifGroupModerator(int userId, int groupId) {
        AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
        int roleId = accountGroupDAO.getUserRoleInGroup(userId, groupId);
        if (roleId == 0) {
            return false;
        }
        return roleId == MODERATOR;
    }

    public List<Group> getAllGroups() {
        GroupDAO groupDAO = new GroupDAO();
        return groupDAO.getAll();
    }

    public void deleteGroup(int id) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            GroupDAO groupDAO = new GroupDAO();
            groupDAO.delete(id);
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.deleteGroup(id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
