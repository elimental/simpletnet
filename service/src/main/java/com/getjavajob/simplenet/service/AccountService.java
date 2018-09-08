package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.common.entity.Picture;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.PicturesDAO;
import com.getjavajob.simplenet.dao.RelationshipDAO;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.service.PasswordEncryptService.checkPass;

public class AccountService {
    private AccountDAO accountDAO;
    private PhoneDAO phoneDAO;
    private RelationshipDAO relationshipDAO;
    private PicturesDAO picturesDAO;

    public AccountService(AccountDAO accountDAO, PhoneDAO phoneDAO, RelationshipDAO relationshipDAO,
                          PicturesDAO picturesDAO) {
        this.accountDAO = accountDAO;
        this.phoneDAO = phoneDAO;
        this.relationshipDAO = relationshipDAO;
        this.picturesDAO = picturesDAO;
    }

    public AccountService(AccountDAO accountDAO, PhoneDAO phoneDAO, RelationshipDAO relationshipDAO) {
        this(accountDAO, phoneDAO, relationshipDAO, null);
    }

    public AccountService(AccountDAO accountDAO, PhoneDAO phoneDAO, PicturesDAO picturesDAO) {
        this(accountDAO, phoneDAO, null, picturesDAO);
    }

    public AccountService(PicturesDAO picturesDAO) {
        this(null, null, null, picturesDAO);
    }

    public AccountService(AccountDAO accountDAO) {
        this(accountDAO, null, null, null);
    }

    public int addAccount(Account account) {
        int id = accountDAO.add(account);
        int userId = id;
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            for (Phone phone : phones) {
                phone.setPhoneOwner(userId);
                phoneDAO.add(phone);
            }
        }
        return id;
    }

    public void addPhoto(Picture picture) {
        picturesDAO.add(picture);
    }

    public void updatePhoto(Picture picture) {
        int userId = picture.getUserId();
        Picture pic = picturesDAO.getByUserId(userId);
        if (pic == null) {
            System.out.println("null");
            picturesDAO.add(picture);
        } else {
            picturesDAO.update(picture);
            System.out.println("nenull");
        }
    }

    public Picture getPhoto(int userId) {
        return picturesDAO.getByUserId(userId);
    }

    public void updateAccount(Account account) {
        accountDAO.update(account);
        int userId = account.getId();
        phoneDAO.deleteByOwnerId(userId);
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            for (Phone phone : phones) {
                phone.setPhoneOwner(userId);
                phoneDAO.add(phone);
            }
        }
    }

    public void deleteAccount(Account account) {
        int userId = account.getId();
        accountDAO.delete(userId);
        phoneDAO.deleteByOwnerId(userId);
    }

    public void addFriend(Account userOne, Account userTwo) {
        relationshipDAO.add(userOne.getId(), userTwo.getId());
    }

    public void deleteFriend(Account userOne, Account userTwo) {
        relationshipDAO.delete(userOne.getId(), userTwo.getId());
    }

    public List<Account> getFriends(int userId) {
        List<Integer> friendsIds = relationshipDAO.getAll(userId);
        List<Account> friends = new ArrayList<>();
        for (Integer id : friendsIds) {
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

    public boolean ifEmailAlreadyPresent(String email) {
        return accountDAO.getByEmail(email) != null;
    }

    public boolean checkLogin(String email, String password) {
        Account account = accountDAO.getByEmail(email);
        if (account == null) {
            return false;
        } else {
            String passHash = account.getPassHash();
            return checkPass(password, passHash);
        }
    }
}
