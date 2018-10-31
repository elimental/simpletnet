package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.dao.dao.AccountDAO;
import com.getjavajob.simplenet.dao.dao.AccountGroupDAO;
import com.getjavajob.simplenet.dao.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.dao.RelationshipDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.ADMINISTRATOR;
import static com.getjavajob.simplenet.service.PasswordEncryptService.checkPass;

@Service
public class AccountService {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private PhoneDAO phoneDAO;
    @Autowired
    private RelationshipDAO relationshipDAO;
    @Autowired
    private AccountGroupDAO accountGroupDAO;

    @Transactional
    public void addAccount(Account account) {
        int userId = accountDAO.add(account);
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            for (Phone phone : phones) {
                phone.setOwner(userId);
                phoneDAO.add(phone);
            }
        }
    }

    @Transactional
    public void updateAccount(Account account) {
        int userId = account.getId();
        Account accountInDb = accountDAO.getById(userId);
        if (account.getPhoto() == null && accountInDb.getPhoto() != null) {
            account.setPhoto(accountInDb.getPhoto());
        }
        accountDAO.update(account);
        phoneDAO.deleteByOwnerId(userId);
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            for (Phone phone : phones) {
                phone.setOwner(userId);
                phoneDAO.add(phone);
            }
        }
    }

    @Transactional
    public void deleteAccount(int id) {
        phoneDAO.deleteByOwnerId(id);
        accountDAO.delete(id);
        relationshipDAO.deleteUser(id);
        accountGroupDAO.deleteUser(id);
    }

    @Transactional
    public void sendFriendRequest(int fromUserId, int toUserId) {
        relationshipDAO.sendFriendRequest(fromUserId, toUserId);
    }

    @Transactional
    public void acceptFriendRequest(int whoAcceptsId, int whoAcceptedId) {
        relationshipDAO.acceptFriend(whoAcceptsId, whoAcceptedId);
    }

    @Transactional
    public void deleteFriend(int userOneId, int userTwoId) {
        relationshipDAO.deleteFriend(userOneId, userTwoId);
    }

    @Transactional
    public void updateUserRole(int userId, int role) {
        accountDAO.updateUserRole(userId, role);
    }

    public List<Account> getFriends(int userId) {
        List<Integer> friendsIds = relationshipDAO.getAllAccepted(userId);
        return makeAccountList(friendsIds);
    }

    public List<Account> getRequestedFriends(int userId) {
        List<Integer> requestedIds = relationshipDAO.getAllRequested(userId);
        return makeAccountList(requestedIds);
    }

    public List<Account> getRequestFromFriends(int userId) {
        List<Integer> requestIds = relationshipDAO.getAllRequest(userId);
        return makeAccountList(requestIds);
    }

    private List<Account> makeAccountList(List<Integer> ids) {
        List<Account> friends = new ArrayList<>();
        for (Integer id : ids) {
            friends.add(accountDAO.getById(id));
        }
        return friends;
    }

    public List<Account> getAllUsers() {
        List<Account> accounts = accountDAO.getAll();
        for (Account account : accounts) {
            int accountId = account.getId();
            List<Phone> phones = phoneDAO.getPhonesByOwnerId(accountId);
            account.setPhones(phones);
        }
        return accounts;
    }

    public Account getUserByEmail(String email) {
        return accountDAO.getByEmail(email);
    }

    public Account getUserById(int id) {
        Account account = accountDAO.getById(id);
        List<Phone> phones = phoneDAO.getPhonesByOwnerId(id);
        if (!phones.isEmpty()) {
            account.setPhones(phones);
        }
        return account;
    }

    public boolean ifEmailAlreadyPresented(String email) {
        return accountDAO.getByEmail(email) != null;
    }

    public boolean checkLogin(String email, String password) {
        Account account = accountDAO.getByEmail(email);
        if (account == null) {
            return false;
        } else {
            String passHash = account.getPassword();
            return checkPass(password, passHash);
        }
    }

    public boolean ifAdmin(int userId) {
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

    public boolean ifAlreadyRequested(int userId, int requestedUserId) {
        return relationshipDAO.checkRequestToOtherUser(userId, requestedUserId);
    }
}
