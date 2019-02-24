package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.dao.repositories.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    public void addAccount() {
        accountService.addAccount(new Account());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    public void updateAccount() {
    }

    @Test
    public void deleteAccount() {
    }

    @Test
    public void getAllAccounts() {
    }

    @Test
    public void sendFriendRequest() {
    }

    @Test
    public void updateAccountRole() {
    }

    @Test
    public void getFriends() {
    }

    @Test
    public void getRequestedFriends() {
    }

    @Test
    public void getRequestFromFriends() {
    }

    @Test
    public void getAccountByEmail() {
    }

    @Test
    public void getAccountById() {
    }

    @Test
    public void ifEmailAlreadyPresented() {
    }

    @Test
    public void ifAdmin() {
    }

    @Test
    public void ifFriend() {
    }

    @Test
    public void ifAlreadyRequested() {
    }

    @Test
    public void getImage() {
    }

    @Test
    public void getWallMessages() {
    }

    @Test
    public void sendWallMessage() {
    }

    @Test
    public void deleteWallMessage() {
    }

    @Test
    public void getTalkersId() {
    }

    @Test
    public void exitFromCommunity() {
    }

    @Test
    public void deleteFriend() {
    }

    @Test
    public void acceptFriendRequest() {
    }

    @Test
    public void getChatMessages() {
    }

    @Test
    public void sendPersonalMessage() {
    }
}