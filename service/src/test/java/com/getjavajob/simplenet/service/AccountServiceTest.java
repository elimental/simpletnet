package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.RelationshipDAO;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private AccountDAO accountDAO;
    private PhoneDAO phoneDAO;
    private RelationshipDAO relationshipDAO;

    @Before
    public void setUp() throws Exception {
        this.accountDAO = mock(AccountDAO.class);
        this.phoneDAO = mock(PhoneDAO.class);
        this.relationshipDAO = mock(RelationshipDAO.class);
    }

    @Test
    public void addAccount() {
        AccountService accountService = new AccountService(accountDAO, phoneDAO, relationshipDAO);
        Account account = new Account();
        Phone homePhone = new Phone();
        List<Phone> homePhones = new ArrayList<>();
        homePhones.add(homePhone);
        Phone workPhone = new Phone();
        List<Phone> workPhones = new ArrayList<>();
        workPhones.add(workPhone);
        account.setHomePhones(homePhones);
        account.setWorkPhones(workPhones);
        when(accountDAO.add(account)).thenReturn(1);
        accountService.addAccount(account);
        homePhones = account.getHomePhones();
        workPhones = account.getWorkPhones();
        for (Phone phone : homePhones) {
            assertEquals(1, phone.getPhoneOwner());
        }
        for (Phone phone : workPhones) {
            assertEquals(1, phone.getPhoneOwner());
        }
    }
}