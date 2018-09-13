package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.RelationshipDAO;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
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

 //   @Test
    public void addAccount() throws SQLException {
        AccountService accountService = new AccountService();
        Account account = new Account();
        Phone phone = new Phone();
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        account.setPhones(phones);
        when(accountDAO.add(account)).thenReturn(1);
        accountService.addAccount(account);
        phones = account.getPhones();
        for (Phone phone1 : phones) {
            assertEquals(1, phone1.getPhoneOwner());
        }
    }
}