package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.*;
import com.getjavajob.simplenet.dao.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.getjavajob.simplenet.common.entity.Role.ADMIN;
import static com.getjavajob.simplenet.common.entity.Role.USER;
import static java.lang.System.currentTimeMillis;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
@Transactional
public class AccountService {

    private static final boolean ACCEPTED_FRIENDSHIP = true;
    private static final boolean ALREADY_REQUESTED_FRIENSHIP = false;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void addAccount(Account account) {
        account.setRole(USER);
        account.setRegDate(new Date(currentTimeMillis()));
        accountRepository.save(account);
    }

    public void updateAccount(Account account) {
        accountRepository.findById(account.getId()).get().updateAccount(account);
    }

    public void deleteSelfAccount(long id) {
        accountRepository.deleteById(id);
    }

    @Secured(value = "ROLE_ADMIN")
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    public void sendFriendRequest(long fromId, long toId) {
        Account from = accountRepository.findById(fromId).get();
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFrom(from);
        friendRequest.setTo(accountRepository.findById(toId).get());
        from.addFriendRequest(friendRequest);
    }

    @Secured(value = "ROLE_ADMIN")
    public void updateAccountRole(long id, Role role) {
        accountRepository.findById(id).get().setRole(role);
    }

    public List<Account> getFriends(long accountId) {
        List<Account> friends = accountRepository.getFriendsFrom(accountId);
        friends.addAll(accountRepository.getFriendsTo(accountId));
        return friends;
    }

    public List<Account> getRequestedFriends(long accountId) {
        return accountRepository.getRequestedFriends(accountId);
    }

    public List<Account> getRequestFromFriends(long accountId) {
        return accountRepository.getRequestFromFriends(accountId);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account getAccountById(long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return null;
        }
        account.getPhones().size();
        return account;
    }

    public boolean ifEmailAlreadyPresented(String email) {
        return accountRepository.findByEmail(email) != null;
    }

    public boolean ifAdmin(long id) {
        Account account = accountRepository.findById(id).get();
        Role role = account.getRole();
        return role == ADMIN;
    }

    public boolean ifFriend(long firstAccountId, long secondAccountId) {
        return !accountRepository.getFriendship(firstAccountId, secondAccountId, ACCEPTED_FRIENDSHIP).isEmpty();
    }

    public boolean ifAlreadyRequested(long firstAccountId, long secondAccountId) {
        return !accountRepository.getFriendship(firstAccountId, secondAccountId, ALREADY_REQUESTED_FRIENSHIP).isEmpty();
    }

    public byte[] getImage(long id) {
        Account account = accountRepository.findById(id).orElse(null);
        return account == null ? null : account.getPhoto();
    }

    public List<WallMessage> getWallMessages(long id) {
        List<WallMessage> wallMessages = accountRepository.findById(id).get().getWallMessages();
        Collections.sort(wallMessages);
        return wallMessages;
    }

    public void sendWallMessage(long accountId, String message) {
        accountRepository.findById(accountId).get().addWallMessage(message);
    }

    public void deleteWallMessage(long messageId, long accountId) {
        accountRepository.findById(accountId).get().removeWallMessage(messageId);
    }

    public Set<Long> getTalkersId(long accountId) {
        Set<Long> talkers = accountRepository.getTalkersIdFrom(accountId);
        talkers.addAll(accountRepository.getTalkersIdTo(accountId));
        return talkers;
    }

    public void exitFromCommunity(long accountId, long communityId) {
        accountRepository.deleteFromCommunity(accountId, communityId);
    }

    public void deleteFriend(long firstAccountId, long secondAccountId) {
        accountRepository.deleteFriend(firstAccountId, secondAccountId);
    }

    public void acceptFriendRequest(long whoAcceptsId, long whoAcceptedId) {
        accountRepository.acceptFriend(whoAcceptsId, whoAcceptedId);
    }

    public List<PersonalMessage> getChatMessages(long firstAccountId, long secondAccountId) {
        return accountRepository.getPersonalMessages(firstAccountId, secondAccountId);
    }

    public void sendPersonalMessage(long accountId, long secondTalkerId, String message) {
        Account account = accountRepository.findById(accountId).get();
        PersonalMessage personalMessage = new PersonalMessage();
        personalMessage.setFrom(account);
        personalMessage.setTo(accountRepository.findById(secondTalkerId).get());
        personalMessage.setText(message);
        personalMessage.setCreateDate(new Date(currentTimeMillis()));
        account.addPersonalMessage(personalMessage);
    }
}
