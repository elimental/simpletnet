package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.entity.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountDAOTest {
    private static Connection connection;
    private static AccountDAO accountDAO;

    @Before
    public void setUp() throws Exception {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("h2.properties"));
            String url = properties.getProperty("database.url");
            String user = properties.getProperty("database.user");
            String password = properties.getProperty("database.password");
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            accountDAO = new AccountDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void getAll() {
        List<Account> accounts = accountDAO.getAll();
        assertEquals(3, accounts.size());
    }

    @Test
    public void getById() {
        Account expected = new Account();
        expected.setId(2);
        expected.setFirstName("Vasya");
        expected.setLastName("Petrov");
        expected.setPatronymicName("Ivanovich");
        expected.setBirthDay(Date.valueOf("1981-12-17"));
        expected.setHomeAddress("Msk");
        expected.setWorkAddress("Msk");
        expected.setEmail("vasya@bk.ru");
        expected.setIcq("49444940");
        expected.setSkype("vasya17");
        expected.setAdditionalInfo("adfasdfasdf");
        Account actual = accountDAO.getById(2);
        assertEquals(expected.toString(), actual.toString());

    }

    @Test
    public void add() {
        Account excepted = new Account();
        excepted.setId(4);
        excepted.setFirstName("Vasya");
        excepted.setLastName("Petrov");
        excepted.setPatronymicName("Ivanovich");
        excepted.setBirthDay(Date.valueOf("1981-12-17"));
        excepted.setHomeAddress("Msk");
        excepted.setWorkAddress("Msk");
        excepted.setEmail("vasya@bk.ru");
        excepted.setIcq("49444940");
        excepted.setSkype("vasya17");
        excepted.setAdditionalInfo("adfasdfasdf");
        accountDAO.add(excepted);
        Account actual = accountDAO.getById(4);
        assertEquals(excepted.toString(), actual.toString());
    }

    @Test
    public void delete() {
        accountDAO.delete(3);
        Account account = accountDAO.getById(3);
        assertNull(account);
    }
}