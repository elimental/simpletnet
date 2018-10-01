package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.AccountGroupDAO;
import com.getjavajob.simplenet.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.RelationshipDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.ADMINISTRATOR;
import static com.getjavajob.simplenet.service.PasswordEncryptService.checkPass;

public class AccountService {
    private static AccountService ourInstance = new AccountService();
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private AccountService() {
    }

    public static AccountService getInstance() {
        return ourInstance;
    }

    public void addAccount(Account account) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountDAO accountDAO = new AccountDAO();
            int userId = accountDAO.add(account);
            List<Phone> phones = account.getPhones();
            if (phones != null) {
                PhoneDAO phoneDAO = new PhoneDAO();
                for (Phone phone : phones) {
                    phone.setPhoneOwner(userId);
                    phoneDAO.add(phone);
                }
            }
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

    public void updateAccount(Account account) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountDAO accountDAO = new AccountDAO();
            int userId = account.getId();
            Account accountInDb = accountDAO.getById(userId);
            if (account.getPhoto() == null && accountInDb.getPhoto() != null) {
                account.setPhoto(accountInDb.getPhoto());
            }
            accountDAO.update(account);
            PhoneDAO phoneDAO = new PhoneDAO();
            phoneDAO.deleteByOwnerId(userId);
            List<Phone> phones = account.getPhones();
            if (phones != null) {
                for (Phone phone : phones) {
                    phone.setPhoneOwner(userId);
                    phoneDAO.add(phone);
                }
            }
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

    public void deleteAccount(int id) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountDAO accountDAO = new AccountDAO();
            PhoneDAO phoneDAO = new PhoneDAO();
            phoneDAO.deleteByOwnerId(id);
            accountDAO.delete(id);
            RelationshipDAO relationshipDAO = new RelationshipDAO();
            relationshipDAO.deleteUser(id);
            AccountGroupDAO accountGroupDAO = new AccountGroupDAO();
            accountGroupDAO.deleteUser(id);
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

    public void sendFriendRequest(int fromUserId, int toUserId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            RelationshipDAO relationshipDAO = new RelationshipDAO();
            relationshipDAO.sendFriendRequest(fromUserId, toUserId);
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

    public void acceptFriendRequest(int whoAcceptsId, int whoAcceptedId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            RelationshipDAO relationshipDAO = new RelationshipDAO();
            relationshipDAO.acceptFriend(whoAcceptsId, whoAcceptedId);
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


    public void deleteFriend(int userOneId, int userTwoId) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            RelationshipDAO relationshipDAO = new RelationshipDAO();
            relationshipDAO.deleteFriend(userOneId, userTwoId);
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

    public List<Account> getFriends(int userId) {
        RelationshipDAO relationshipDAO = new RelationshipDAO();
        List<Integer> friendsIds = relationshipDAO.getAllAccepted(userId);
        return makeAccountList(friendsIds);
    }

    public List<Account> getRequestedFriends(int userId) {
        RelationshipDAO relationshipDAO = new RelationshipDAO();
        List<Integer> requestedIds = relationshipDAO.getAllRequested(userId);
        return makeAccountList(requestedIds);
    }

    public List<Account> getRequestFromFriends(int userId) {
        RelationshipDAO relationshipDAO = new RelationshipDAO();
        List<Integer> requestIds = relationshipDAO.getAllRequest(userId);
        return makeAccountList(requestIds);
    }

    private List<Account> makeAccountList(List<Integer> ids) {
        List<Account> friends = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        for (Integer id : ids) {
            friends.add(accountDAO.getById(id));
        }
        return friends;
    }

    public List<Account> getAllUsers() {
        AccountDAO accountDAO = new AccountDAO();
        List<Account> accounts = accountDAO.getAll();
        PhoneDAO phoneDAO = new PhoneDAO();
        for (Account account : accounts) {
            int accountId = account.getId();
            List<Phone> phones = phoneDAO.getPhonesByOwnerId(accountId);
            account.setPhones(phones);
        }
        return accounts;
    }

    public Account getUserByEmail(String email) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.getByEmail(email);
    }

    public Account getUserById(int id) {
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getById(id);
        PhoneDAO phoneDAO = new PhoneDAO();
        List<Phone> phones = phoneDAO.getPhonesByOwnerId(id);
        if (!phones.isEmpty()) {
            account.setPhones(phones);
        }
        return account;
    }

    public boolean ifEmailAlreadyPresent(String email) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.getByEmail(email) != null;
    }

    public boolean checkLogin(String email, String password) {
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getByEmail(email);
        if (account == null) {
            return false;
        } else {
            String passHash = account.getPassHash();
            return checkPass(password, passHash);
        }
    }

    public boolean ifAdmin(int userId) {
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getById(userId);
        int roleId = account.getRole();
        return roleId == ADMINISTRATOR;
    }

    public boolean ifFriend(int userOneId, int userTwoId) {
        List<Account> friends = getFriends(userOneId);
        for (Account account : friends) {
            if (account.getId() == userTwoId) {
                return true;
            }
        }
        return false;
    }

    public void updateUserRole(int userId, int role) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.updateUserRole(userId, role);
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

    public boolean ifAlreadyRequested(int userId, int requestedUserId) {
        RelationshipDAO relationshipDAO = new RelationshipDAO();
        return relationshipDAO.checkRequestToOtherUser(userId,requestedUserId);
    }
}
