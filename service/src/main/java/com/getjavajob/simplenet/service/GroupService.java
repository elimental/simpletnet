package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.AccountGroupDAO;
import com.getjavajob.simplenet.dao.GroupDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.GROUP_MODERATOR;

public class GroupService {
    private static GroupService ourInstance = new GroupService();
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private GroupService() {
    }

    public static GroupService getInstance() {
        return ourInstance;
    }

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

    public void addGroup(Group group) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            GroupDAO groupDAO = new GroupDAO();
            int id = groupDAO.add(group);
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.addGroup(group.getOwner(), id);
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

    public void sendGroupRequest(int userId, int groupId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.sendGroupRequest(userId, groupId);
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

    public void acceptGroupRequest(int userId, int groupId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.acceptGroupRequest(userId, groupId);
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

    public void rejectGroupRequest(int userId, int groupId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.rejectGroupRequest(userId, groupId);
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

    public List<Account> getCandidates(int groupId) {
        AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
        AccountDAO accountDAO = new AccountDAO();
        List<Integer> candidatesIds = accountGroupDAO.getCandidatesIds(groupId);
        List<Account> candidates = new ArrayList<>();
        for (Integer i:candidatesIds) {
            candidates.add(accountDAO.getById(i));
        }
        return candidates;
    }

    public boolean ifGroupOwner(int userId, int groupId) {
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.getById(groupId);
        return group.getOwner() == userId;
    }

    public boolean ifGroupMember(int userId, int grouId) {
        List<Integer> userGroupsId = getUserGroupsId(userId);
        return userGroupsId.contains(grouId);
    }

    public boolean ifGroupModerator(int userId, int groupId) {
        AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
        int roleId = accountGroupDAO.getUserRoleInGroup(userId, groupId);
        if (roleId == 0) {
            return false;
        }
        return roleId == GROUP_MODERATOR;
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

    public List<Account> getMembers(int groupId) {
        AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
        AccountDAO accountDAO = new AccountDAO();
        List<Integer> userIds = accountGroupDAO.getMemberIds(groupId);
        List<Account> members = new ArrayList<>();
        for (Integer i:userIds) {
            members.add(accountDAO.getById(i));
        }
        return members;
    }

    public boolean ifAlreadyRequested(int userId, int groupId) {
        AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
        List<Integer> candidates = accountGroupDAO.getCandidatesIds(groupId);
        return candidates.contains(userId);
    }

    public void makeModerator(int userId, int groupId) {
         Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.makeModerator(userId, groupId);
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

    public void deleteFromGroup(int userId, int groupId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.deleteFromGroup(userId, groupId);
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
