//package com.getjavajob.simplenet.service;
//
//import com.getjavajob.simplenet.dao.dao.AccountDAO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AccountServiceTest {
//
//    @Mock
//    AccountDAO accountDAO;
//
////    @Mock
////    PhoneDAO phoneDAO;
////
////    @Mock
////    RelationshipDAO relationshipDAO;
////
////    @Mock
////    AccountGroupDAO accountGroupDAO;
//
//    @InjectMocks
//    AccountService accountService;
//
//    @Test
//    public void addAccount() {
////        Account account = getAccount();
////        when(accountDAO.add(account)).thenReturn(5);
////        accountService.addAccount(account);
////        verify(accountDAO).add(account);
////        verify(phoneDAO, never()).add(any(Phone.class));
////        List<Phone> phones = getPhonesWithoutOwner();
////        account.setPhones(phones);
////        accountService.addAccount(account);
////        verify(phoneDAO, times(2)).add(any(Phone.class));
////        assertEquals(5, phones.get(0).getOwner());
////        assertEquals(5, phones.get(1).getOwner());
//    }
//
////    @Test
////    public void updateAccount() {
////        Account account = getAccount();
////        List<Phone> phones = getPhonesWithoutOwner();
////        account.setPhones(phones);
////        Account accountFromDb = getAccount();
////        accountFromDb.setPhoto(new byte[]{1,2,3});
////        when(accountDAO.getById(1)).thenReturn(accountFromDb);
////        accountService.updateAccount(account);
////        verify(accountDAO).update(account);
////        assertArrayEquals(account.getPhoto(), accountFromDb.getPhoto());
////        assertEquals(1, phones.get(0).getOwner());
////        assertEquals(1, phones.get(1).getOwner());
////        verify(phoneDAO, times(2)).add(any(Phone.class));
////        verify(phoneDAO).deleteByOwnerId(anyInt());
////    }
////
////    @Test
////    public void deleteAccount() {
////        accountService.deleteAccount(1);
////        verify(phoneDAO).deleteByOwnerId(1);
////        verify(accountDAO).delete(1);
////        verify(relationshipDAO).deleteUser(1);
////        verify(accountGroupDAO).deleteUser(1);
////    }
////
////    @Test
////    public void sendFriendRequest() {
////        accountService.sendFriendRequest(1,2);
////        verify(relationshipDAO).sendFriendRequest(1,2);
////    }
////
////    @Test
////    public void acceptFriendRequest() {
////        accountService.acceptFriendRequest(1,2);
////        verify(relationshipDAO).acceptFriend(1,2);
////    }
////
////    @Test
////    public void deleteFriend() {
////        accountService.deleteFriend(1,2);
////        verify(relationshipDAO).deleteFriend(1,2);
////    }
////
////    @Test
////    public void updateUserRole() {
////        accountService.updateUserRole(1,2);
////        verify(accountDAO).updateUserRole(1,2);
////    }
////
////    @Test
////    public void getFriends() {
////        List<Integer> friendsId = new ArrayList<>();
////        friendsId.add(7);
////        friendsId.add(10);
////        friendsId.add(21);
////        when(relationshipDAO.getAllAccepted(1)).thenReturn(friendsId);
////        List<Account> exceptedAccounts = getAccoutList();
////        when(accountDAO.getById(7)).thenReturn(exceptedAccounts.get(0));
////        when(accountDAO.getById(10)).thenReturn(exceptedAccounts.get(1));
////        when(accountDAO.getById(21)).thenReturn(exceptedAccounts.get(2));
////        List<Account> actualFriends = accountService.getFriends(1);
////        verify(relationshipDAO).getAllAccepted(1);
////        verify(accountDAO, times(3)).getById(anyInt());
////        assertEquals(exceptedAccounts, actualFriends);
////    }
////
////    @Test
////    public void getRequestedFriends() {
////        List<Integer> friendsId = new ArrayList<>();
////        friendsId.add(7);
////        friendsId.add(10);
////        friendsId.add(21);
////        when(relationshipDAO.getAllRequested(1)).thenReturn(friendsId);
////        List<Account> exceptedAccounts = getAccoutList();
////        when(accountDAO.getById(7)).thenReturn(exceptedAccounts.get(0));
////        when(accountDAO.getById(10)).thenReturn(exceptedAccounts.get(1));
////        when(accountDAO.getById(21)).thenReturn(exceptedAccounts.get(2));
////        List<Account> actualFriends = accountService.getRequestedFriends(1);
////        verify(relationshipDAO).getAllRequested(1);
////        verify(accountDAO, times(3)).getById(anyInt());
////        assertEquals(exceptedAccounts, actualFriends);
////    }
////
////    @Test
////    public void getRequestFromFriends() {
////         List<Integer> friendsId = new ArrayList<>();
////        friendsId.add(7);
////        friendsId.add(10);
////        friendsId.add(21);
////        when(relationshipDAO.getAllRequest(1)).thenReturn(friendsId);
////        List<Account> exceptedAccounts = getAccoutList();
////        when(accountDAO.getById(7)).thenReturn(exceptedAccounts.get(0));
////        when(accountDAO.getById(10)).thenReturn(exceptedAccounts.get(1));
////        when(accountDAO.getById(21)).thenReturn(exceptedAccounts.get(2));
////        List<Account> actualFriends = accountService.getRequestFromFriends(1);
////        verify(relationshipDAO).getAllRequest(1);
////        verify(accountDAO, times(3)).getById(anyInt());
////        assertEquals(exceptedAccounts, actualFriends);
////    }
////
////    @Test
////    public void getAllUsers() {
////        List<Account> expectedAccounts = getAccoutList();
////        List<Phone> account7Phones = expectedAccounts.get(0).getPhones();
////        List<Phone> account10Phones = expectedAccounts.get(1).getPhones();
////        List<Phone> account21Phones = expectedAccounts.get(2).getPhones();
////        List<Account> accountsWithousPhones = getAccoutList();
////        for (Account account: accountsWithousPhones) {
////            account.setPhones(null);
////        }
////        when(accountDAO.getAll()).thenReturn(accountsWithousPhones);
////        when(phoneDAO.getPhonesByOwnerId(7)).thenReturn(account7Phones);
////        when(phoneDAO.getPhonesByOwnerId(10)).thenReturn(account10Phones);
////        when(phoneDAO.getPhonesByOwnerId(21)).thenReturn(account21Phones);
////        List<Account> actualAccounts = accountService.getAllUsers();
////        verify(accountDAO).getAll();
////        verify(phoneDAO, times(3)).getPhonesByOwnerId(anyInt());
////        assertEquals(expectedAccounts, actualAccounts);
////
////    }
////
////    @Test
////    public void getUserByEmail() {
////        Account exceptedAccount = getAccount();
////        when(accountDAO.getByEmail("vasya@mail.ru")).thenReturn(exceptedAccount);
////        Account actualAccount = accountService.getUserByEmail("vasya@mail.ru");
////        verify(accountDAO).getByEmail(anyString());
////        assertEquals(exceptedAccount, actualAccount);
////    }
////
////    @Test
////    public void getUserById() {
////        Account expectedAccount = getAccoutList().get(0);
////        List<Phone> accountPhones = expectedAccount.getPhones();
////        Account accountWithoutPhones = getAccoutList().get(0);
////        accountWithoutPhones.setPhones(null);
////        when(accountDAO.getById(7)).thenReturn(accountWithoutPhones);
////        when(phoneDAO.getPhonesByOwnerId(7)).thenReturn(accountPhones);
////        Account actualAccount = accountService.getUserById(7);
////        verify(accountDAO).getById(7);
////        verify(phoneDAO).getPhonesByOwnerId(7);
////        assertEquals(expectedAccount, actualAccount);
////    }
////
////    @Test
////    public void ifEmailAlreadyPresented() {
////        when(accountDAO.getByEmail("vasya@mail.ru")).thenReturn(getAccount());
////        when(accountDAO.getByEmail("petya@mail.ru")).thenReturn(null);
////        accountService.ifEmailAlreadyPresented("vasya@mail.ru");
////        verify(accountDAO).getByEmail("vasya@mail.ru");
////        assertTrue(accountService.ifEmailAlreadyPresented("vasya@mail.ru"));
////        assertFalse(accountService.ifEmailAlreadyPresented("petya@mail.ru"));
////    }
////
////    @Test
////    public void checkLogin() {
////        when(accountDAO.getByEmail("vasya@mail.ru")).thenReturn(getAccount());
////        assertTrue(accountService.checkLogin("vasya@mail.ru", "password"));
////        assertFalse(accountService.checkLogin("vasya@mail.ru", "wrong password"));
////        verify(accountDAO, times(2)).getByEmail(anyString());
////    }
////
////    @Test
////    public void ifAdmin() {
////        Account account = getAccount();
////        when(accountDAO.getById(7)).thenReturn(account);
////        assertFalse(accountService.ifAdmin(7));
////        account.setRole(2);
////        assertTrue(accountService.ifAdmin(7));
////        verify(accountDAO, times(2)).getById(7);
////    }
////
////    @Test
////    public void ifFriend() {
////        List<Integer> friendsId = new ArrayList<>();
////        friendsId.add(7);
////        friendsId.add(10);
////        friendsId.add(21);
////        when(relationshipDAO.getAllAccepted(1)).thenReturn(friendsId);
////        List<Account> accounts = getAccoutList();
////        when(accountDAO.getById(7)).thenReturn(accounts.get(0));
////        when(accountDAO.getById(10)).thenReturn(accounts.get(1));
////        when(accountDAO.getById(21)).thenReturn(accounts.get(2));
////        assertTrue(accountService.ifFriend(1,10));
////        assertFalse(accountService.ifFriend(1,11));
////    }
////
////    @Test
////    public void ifAlreadyRequested() {
////        accountService.ifAlreadyRequested(1,2);
////        verify(relationshipDAO).checkRequestToOtherUser(1,2);
////    }
////
////    private static Account getAccount() {
////        Account account = new Account();
////        account.setId(1);
////        account.setEmail("vasya@mail.ru");
////        account.setPassword(genHash("password"));
////        account.setRole(1);
////        return account;
////    }
////
////    private static List<Phone> getPhonesWithoutOwner() {
////        List<Phone> phones = new ArrayList<>();
////        Phone phone1 = new Phone();
////        phone1.setType(1);
////        phone1.setNumber("12345");
////        Phone phone2 = new Phone();
////        phone1.setType(2);
////        phone1.setNumber("67890");
////        phones.add(phone1);
////        phones.add(phone2);
////        return phones;
////    }
////
////    private static List<Account> getAccoutList() {
////        Account account7 = new Account();
////        account7.setId(7);
////        Phone phone1 = new Phone();
////        phone1.setNumber("123");
////        phone1.setOwner(7);
////        Phone phone2 = new Phone();
////        phone1.setNumber("567");
////        phone1.setOwner(7);
////        Phone phone3 = new Phone();
////        phone1.setNumber("890");
////        phone1.setOwner(7);
////        List<Phone> accountPhones = new ArrayList<>();
////        accountPhones.add(phone1);
////        accountPhones.add(phone2);
////        accountPhones.add(phone3);
////        account7.setPhones(accountPhones);
////
////        Account account10 = new Account();
////        account10.setId(10);
////        phone1 = new Phone();
////        phone1.setNumber("654");
////        phone1.setOwner(10);
////        phone2 = new Phone();
////        phone1.setNumber("763");
////        phone1.setOwner(10);
////        phone3 = new Phone();
////        phone1.setNumber("387");
////        phone1.setOwner(10);
////        accountPhones = new ArrayList<>();
////        accountPhones.add(phone1);
////        accountPhones.add(phone2);
////        accountPhones.add(phone3);
////        account10.setPhones(accountPhones);
////
////        Account account21 = new Account();
////        account21.setId(21);
////        phone1 = new Phone();
////        phone1.setNumber("999");
////        phone1.setOwner(21);
////        phone2 = new Phone();
////        phone1.setNumber("666");
////        phone1.setOwner(21);
////        phone3 = new Phone();
////        phone1.setNumber("777");
////        phone1.setOwner(21);
////        accountPhones = new ArrayList<>();
////        accountPhones.add(phone1);
////        accountPhones.add(phone2);
////        accountPhones.add(phone3);
////        account21.setPhones(accountPhones);
////
////        List<Account> accounts = new ArrayList<>();
////        accounts.add(account7);
////        accounts.add(account10);
////        accounts.add(account21);
////        return accounts;
////    }
//}