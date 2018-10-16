package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.dao.config.DAOConfig;
import com.getjavajob.simplenet.dao.config.DAOTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
@Sql(value = {"classpath:drop_db.sql", "classpath:create_db.sql", "classpath:populate_db.sql"},
        executionPhase = BEFORE_TEST_METHOD)
public class AccountDAOTest {

    @Autowired
    private AccountDAO accountDAO;

    @Test
    public void getAll() {
        List<Account> expectedAccounts = new ArrayList<>();
        Account expected = new Account();
        expected.setId(1);
        expected.setFirstName("Dima");
        expected.setLastName("Andreev");
        expected.setPatronymicName("Borisovich");
        expected.setBirthDay(Date.valueOf("1978-02-13"));
        expected.setEmail("elimental@bk.ru");
        expected.setIcq("49224940");
        expected.setSkype("elimental13");
        expected.setAdditionalInfo("adfasdfasdf");
        expected.setPassHash("DSFGDSGFDSGFDSGFDSGFSASFASDF");
        expected.setRegDate(Date.valueOf("2018-01-01"));
        expected.setRole(2);
        expectedAccounts.add(expected);

        Account expected2 = new Account();
        expected2.setId(2);
        expected2.setFirstName("Vasya");
        expected2.setLastName("Petrov");
        expected2.setPatronymicName("Alekseevich");
        expected2.setBirthDay(Date.valueOf("1980-05-12"));
        expected2.setEmail("vasya@bk.ru");
        expected2.setIcq("12345678");
        expected2.setSkype("vasiliy44");
        expected2.setAdditionalInfo("agfsdgfdgfds");
        expected2.setPassHash("WTRWE%$#@$%GWERGWEGWEGWEGRWE");
        expected2.setRegDate(Date.valueOf("2018-02-02"));
        expected2.setRole(1);
        expectedAccounts.add(expected2);
        List<Account> actual = accountDAO.getAll();
        assertEquals(expectedAccounts, actual);
    }

    @Test
    public void getById() {
        Account expected = new Account();
        expected.setId(2);
        expected.setFirstName("Vasya");
        expected.setLastName("Petrov");
        expected.setPatronymicName("Alekseevich");
        expected.setBirthDay(Date.valueOf("1980-05-12"));
        expected.setEmail("vasya@bk.ru");
        expected.setIcq("12345678");
        expected.setSkype("vasiliy44");
        expected.setAdditionalInfo("agfsdgfdgfds");
        expected.setPassHash("WTRWE%$#@$%GWERGWEGWEGWEGRWE");
        expected.setRegDate(Date.valueOf("2018-02-02"));
        expected.setRole(1);
        Account actual = accountDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void add() {
        Account expectedAccount = new Account();
        expectedAccount.setId(3);
        expectedAccount.setFirstName("Masha");
        expectedAccount.setLastName("Ivanova");
        expectedAccount.setPatronymicName("Sergeevna");
        expectedAccount.setBirthDay(Date.valueOf("1980-05-12"));
        expectedAccount.setEmail("masha@bk.ru");
        expectedAccount.setIcq("12345678");
        expectedAccount.setSkype("manya33");
        expectedAccount.setAdditionalInfo("agfsdgfdgfds");
        expectedAccount.setPassHash("WTRWE%$#@$%GWERGWEGWEGWEGRWE");
        expectedAccount.setRegDate(Date.valueOf("2018-02-02"));
        expectedAccount.setRole(1);
        Integer expectedId = 3;
        Integer actualId = accountDAO.add(expectedAccount);
        Account actualAccount = accountDAO.getById(3);
        assertEquals(expectedId, actualId);
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void delete() {
        accountDAO.delete(1);
        Account account = accountDAO.getById(1);
        assertNull(account);
    }

    @Test
    public void update() {
        Account expected = new Account();
        expected.setId(1);
        expected.setFirstName("Dmitriy");
        expected.setLastName("Andreev");
        expected.setPatronymicName("Borisovich");
        expected.setBirthDay(Date.valueOf("1978-02-13"));
        expected.setEmail("elimental@mail.ru");
        expected.setIcq("49224940");
        expected.setSkype("elimental13");
        expected.setAdditionalInfo("just human");
        expected.setPassHash("DSFGDSGFDSGFDSGFDSGFSASFASDF");
        expected.setRegDate(Date.valueOf("2018-01-01"));
        expected.setRole(2);
        accountDAO.update(expected);
        Account actual = accountDAO.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void getByEmail() {
        Account expected = new Account();
        expected.setId(2);
        expected.setFirstName("Vasya");
        expected.setLastName("Petrov");
        expected.setPatronymicName("Alekseevich");
        expected.setBirthDay(Date.valueOf("1980-05-12"));
        expected.setEmail("vasya@bk.ru");
        expected.setIcq("12345678");
        expected.setSkype("vasiliy44");
        expected.setAdditionalInfo("agfsdgfdgfds");
        expected.setPassHash("WTRWE%$#@$%GWERGWEGWEGWEGRWE");
        expected.setRegDate(Date.valueOf("2018-02-02"));
        expected.setRole(1);
        Account actual = accountDAO.getByEmail("vasya@bk.ru");
        Account nonexistent = accountDAO.getByEmail("nobody@mail.ru");
        assertEquals(expected, actual);
        assertNull(nonexistent);
    }

    @Test
    public void updateUserRole() {
        Account expected = new Account();
        expected.setId(1);
        expected.setFirstName("Dima");
        expected.setLastName("Andreev");
        expected.setPatronymicName("Borisovich");
        expected.setBirthDay(Date.valueOf("1978-02-13"));
        expected.setEmail("elimental@bk.ru");
        expected.setIcq("49224940");
        expected.setSkype("elimental13");
        expected.setAdditionalInfo("adfasdfasdf");
        expected.setPassHash("DSFGDSGFDSGFDSGFDSGFSASFASDF");
        expected.setRegDate(Date.valueOf("2018-01-01"));
        expected.setRole(1);
        accountDAO.updateUserRole(expected.getId(), expected.getRole());
        Account actual = accountDAO.getById(expected.getId());
        assertEquals(expected, actual);
    }
}