package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.RelationshipDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;
    private PhoneDAO phoneDAO;
    private RelationshipDAO relationshipDAO;

    public AccountService(AccountDAO accountDAO, PhoneDAO phoneDAO, RelationshipDAO relationshipDAO) {
        this.accountDAO = accountDAO;
        this.phoneDAO = phoneDAO;
        this.relationshipDAO = relationshipDAO;
    }

    public void addAccount(Account account) {
        int phoneOwner = accountDAO.add(account);
        List<Phone> homePhones = account.getHomePhones();
        if (homePhones != null) {
            for (Phone phone : homePhones) {
                phone.setPhoneOwner(phoneOwner);
                phoneDAO.add(phone);
            }
        }
        List<Phone> workPhones = account.getWorkPhones();
        if (workPhones != null) {
            for (Phone phone : workPhones) {
                phone.setPhoneOwner(phoneOwner);
                phoneDAO.add(phone);
            }
        }
    }

    public void updateAccount(Account account) {
        accountDAO.update(account);
        int userId = account.getId();
        phoneDAO.deleteByOwnerId(userId);
        List<Phone> homePhones = account.getHomePhones();
        if (homePhones != null) {
            for (Phone phone : homePhones) {
                phoneDAO.add(phone);
            }
        }
        List<Phone> workPhones = account.getWorkPhones();
        if (workPhones != null) {
            for (Phone phone : workPhones) {
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
}
