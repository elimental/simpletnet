package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.*;
import com.getjavajob.simplenet.dao.dao.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.getjavajob.simplenet.common.entity.Role.ADMINISTRATOR;
import static com.getjavajob.simplenet.common.entity.Role.USER;
import static com.getjavajob.simplenet.service.PasswordEncryptService.checkPass;
import static com.getjavajob.simplenet.service.PasswordEncryptService.genHash;
import static java.lang.System.currentTimeMillis;

@Service
@Transactional
public class AccountService {

    private static final boolean ACCEPTED_FRIENDSHIP = true;
    private static final boolean ALREADY_REQUESTED_FRIENSHIP = false;

    private AccountDAO accountDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void addAccount(Account account) {
        account.setRole(USER);
        account.setPassword(genHash(account.getPassword()));
        account.setRegDate(new Date(currentTimeMillis()));
        accountDAO.add(account);
    }

    public void updateAccount(Account account) {
        accountDAO.get(account.getId()).updateAccount(account);
    }

    public void deleteAccount(Long id) {
        accountDAO.deleteById(id);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAll();
    }

    public void sendFriendRequest(long fromId, long toId) {
        Account from = accountDAO.get(fromId);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFrom(from);
        friendRequest.setTo(accountDAO.get(toId));
        from.addFriendRequest(friendRequest);
    }

    public void updateAccountRole(Long accountId, Role role) {
        accountDAO.get(accountId).setRole(role);
    }

    public List<Account> getFriends(Long accountId) {
        return accountDAO.getFriends(accountId);
    }

    public List<Account> getRequestedFriends(long accountId) {
        return accountDAO.getRequestedFriends(accountId);
    }

    public List<Account> getRequestFromFriends(long accountId) {
        return accountDAO.getRequestFromFriends(accountId);
    }

    public Account getAccountByEmail(String email) {
        return accountDAO.getByEmail(email);
    }

    public Account getAccountById(Long id) {
        Account account = accountDAO.get(id);
        if (account == null) {
            return null;
        }
        account.getPhones().size();
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

    public boolean ifAdmin(Long userId) {
        Account account = accountDAO.get(userId);
        Role role = account.getRole();
        return role == ADMINISTRATOR;
    }

    public boolean ifFriend(Long firstAccountId, Long secondAccountId) {
        return accountDAO.getFriendship(firstAccountId, secondAccountId, ACCEPTED_FRIENDSHIP) != null;
    }

    public boolean ifAlreadyRequested(Long firstAccountId, Long secondAccountId) {
        return accountDAO.getFriendship(firstAccountId, secondAccountId, ALREADY_REQUESTED_FRIENSHIP) != null;
    }

    public byte[] getImage(Long id) {
        Account account = accountDAO.get(id);
        return account == null ? null : account.getPhoto();
    }

    public List<WallMessage> getWallMessages(Long id) {
        List<WallMessage> wallMessages = accountDAO.get(id).getWallMessages();
        Collections.sort(wallMessages);
        return wallMessages;
    }

    public void sendWallMessage(long accountId, String message) {
        accountDAO.get(accountId).addWallMessage(message);
    }

    public void deleteWallMessage(long messageId, long accountId) {
        accountDAO.get(accountId).removeWallMessage(messageId);
    }

    public Set<Long> getTalkersId(long accountId) {
        return accountDAO.getTalkersId(accountId);
    }

    public void exitFromCommunity(long accountId, long communityId) {
        accountDAO.deleteFromCommunity(accountId, communityId);
    }

    public void deleteFriend(long firstAccountId, long secondAccountId) {
        accountDAO.deleteFriend(firstAccountId, secondAccountId);
    }

    public void acceptFriendRequest(long whoAcceptsId, long whoAcceptedId) {
        accountDAO.acceptFriend(whoAcceptsId, whoAcceptedId);
    }

    public List<PersonalMessage> getChatMessages(long firstAccountId, long secondAccountId) {
        return accountDAO.getPersonalMessages(firstAccountId, secondAccountId);
    }

    public void sendPersonalMessage(long accountId, long secondTalkerId, String message) {
        Account account = accountDAO.get(accountId);
        PersonalMessage personalMessage = new PersonalMessage();
        personalMessage.setFrom(account);
        personalMessage.setTo(accountDAO.get(secondTalkerId));
        personalMessage.setText(message);
        personalMessage.setCreateDate(new Date(currentTimeMillis()));
        account.addPersonalMessage(personalMessage);
    }
}
