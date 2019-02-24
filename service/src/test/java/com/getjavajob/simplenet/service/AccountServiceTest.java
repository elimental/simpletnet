package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.dao.repositories.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static com.getjavajob.simplenet.common.entity.Role.ADMIN;
import static com.getjavajob.simplenet.common.entity.Role.USER;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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
        Account account = new Account();
        account.setId(1L);
        account.setPhones(new ArrayList<>());
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.updateAccount(account);
        verify(accountRepository).findById(any(Long.class));
    }

    @Test
    public void deleteAccount() {
        accountService.deleteAccount(1L, 1L);
        verify(accountRepository).deleteById(1L);
    }

    @Test
    public void getAllAccounts() {
        accountService.getAllAccounts();
        verify(accountRepository).findAll();
    }

    @Test
    public void sendFriendRequest() {
        Account account = new Account();
        account.setId(1L);
        account.setRequestsToOtherAccount(new ArrayList<>());
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.sendFriendRequest(1L, 2L);
        verify(accountRepository, times(2)).findById(any(Long.class));
    }

    @Test
    public void updateAccountRole() {
        Account account = new Account();
        account.setId(1L);
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.updateAccountRole(1L, ADMIN);
        verify(accountRepository).findById(1L);
    }

    @Test
    public void getFriends() {
        accountService.getFriends(1L);
        verify(accountRepository).getFriendsFrom(1L);
        verify(accountRepository).getFriendsTo(1L);
    }

    @Test
    public void getRequestedFriends() {
        accountService.getRequestedFriends(1L);
        verify(accountRepository).getRequestedFriends(1L);
    }

    @Test
    public void getRequestFromFriends() {
        accountService.getRequestFromFriends(1L);
        verify(accountRepository).getRequestFromFriends(1L);
    }

    @Test
    public void getAccountByEmail() {
        accountService.getAccountByEmail("email@bk.ru");
        verify(accountRepository).findByEmail("email@bk.ru");
    }

    @Test
    public void getAccountById() {
        Account account = new Account();
        account.setId(1L);
        account.setPhones(new ArrayList<>());
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.getAccountById(1L);
        verify(accountRepository).findById(1L);

        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertNull(accountService.getAccountById(1L));
    }

    @Test
    public void ifEmailAlreadyPresented() {
        accountService.ifEmailAlreadyPresented("mail@bk.ru");
        verify(accountRepository).findByEmail("mail@bk.ru");
    }

    @Test
    public void ifAdmin() {
        Account account = new Account();
        account.setId(1L);
        account.setRole(USER);
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);

        assertFalse(accountService.ifAdmin(1L));
        verify(accountRepository).findById(1L);

        account.setRole(ADMIN);
        assertTrue(accountService.ifAdmin(1L));
    }

    @Test
    public void ifFriend() {
        accountService.ifFriend(1L, 2L);
        verify(accountRepository).getFriendship(1L, 2L, true);
    }

    @Test
    public void ifAlreadyRequested() {
        accountService.ifAlreadyRequested(1L, 2L);
        verify(accountRepository).getFriendship(1L, 2L, false);
    }

    @Test
    public void getImage() {
        Account account = new Account();
        account.setId(1L);
        byte[] exceptedPhoto = new byte[]{1, 2, 3};
        account.setPhoto(exceptedPhoto);
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        byte[] actualPhoto = accountService.getImage(1L);
        verify(accountRepository).findById(1L);
        assertArrayEquals(exceptedPhoto, actualPhoto);

        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertNull(accountService.getImage(1L));
    }

    @Test
    public void getWallMessages() {
        Account account = new Account();
        account.setId(1L);
        account.setWallMessages(new ArrayList<>());
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.getWallMessages(1L);
        verify(accountRepository).findById(1L);
    }

    @Test
    public void sendWallMessage() {
        Account account = new Account();
        account.setId(1L);
        account.setWallMessages(new ArrayList<>());
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.sendWallMessage(1L, "text");
        verify(accountRepository).findById(1L);
    }

    @Test
    public void deleteWallMessage() {
        Account account = new Account();
        account.setId(1L);
        account.setWallMessages(new ArrayList<>());
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.deleteWallMessage(2L, 1L);
        verify(accountRepository).findById(1L);
    }

    @Test
    public void getTalkersId() {
        accountService.getTalkersId(1L);
        verify(accountRepository).getTalkersIdFrom(1L);
        verify(accountRepository).getTalkersIdTo(1L);
    }

    @Test
    public void exitFromCommunity() {
        accountService.exitFromCommunity(1L, 2L);
        verify(accountRepository).deleteFromCommunity(1L, 2L);
    }

    @Test
    public void deleteFriend() {
        accountService.deleteFriend(1L, 2L);
        verify(accountRepository).deleteFriend(1L, 2L);
    }

    @Test
    public void acceptFriendRequest() {
        accountService.acceptFriendRequest(1L, 2L);
        verify(accountRepository).acceptFriend(1L, 2L);
    }

    @Test
    public void getChatMessages() {
        accountService.getChatMessages(1L, 2L);
        verify(accountRepository).getPersonalMessages(1L, 2L);
    }

    @Test
    public void sendPersonalMessage() {
        Account account = new Account();
        account.setId(1L);
        account.setFromAccount(new ArrayList<>());
        account.setWallMessages(new ArrayList<>());
        Optional<Account> accountOptional = Optional.of(account);
        when(accountRepository.findById(any(Long.class))).thenReturn(accountOptional);
        accountService.sendPersonalMessage(1L, 2L, "text");
        verify(accountRepository).findById(1L);
        verify(accountRepository).findById(2L);
    }
}