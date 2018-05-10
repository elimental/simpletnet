package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountDAOTest {
    private static Connection connection;
    private static AccountDAO accountDAO;

    @Before
    public void setUp() throws Exception {
        this.connection = DBConnectionPool.getInstance().getConnection();
        connection.createStatement().executeUpdate("CREATE TABLE account (" +
                "  userId INT NOT NULL AUTO_INCREMENT," +
                "  firstName VARCHAR(45) NOT NULL," +
                "  lastName VARCHAR(45) NOT NULL," +
                "  patronymicName VARCHAR(45) NOT NULL," +
                "  birthDay DATE NOT NULL," +
                "  homeAddress VARCHAR(45) NULL," +
                "  workAddress VARCHAR(45) NULL," +
                "  email VARCHAR(45) NOT NULL," +
                "  icq VARCHAR(45) NULL," +
                "  skype VARCHAR(45) NULL," +
                "  additionalInfo VARCHAR(1000) NULL," +
                "  PRIMARY KEY (userId)," +
                "  UNIQUE INDEX id_UNIQUE (userId ASC))");
        connection.createStatement().executeUpdate("INSERT INTO account (firstName, lastName, patronymicName, birthDay," +
                " homeAddress, workAddress, email, icq, skype, additionalInfo)" +
                "  VALUES ('Dima', 'Andreev', 'Borisovich', '1978-02-13', 'Spb', 'Spb', 'elimental@bk.ru', '49224940'," +
                " 'elimetal13', 'adfasdfasdf'), ('Vasya','Petrov', 'Ivanovich', '1981-12-17', 'Msk', 'Msk'," +
                " 'vasya@bk.ru', '49444940', 'vasya17', 'adfasdfasdf'), ('Petya','Ivanov', 'Petrovich', '1970-06-21'," +
                " NULL, NULL, 'petya@bk.ru', '43444940', 'petya21', 'adfasdfasdf')");
        connection.commit();
        connection.close();
        accountDAO = new AccountDAO();
    }

    @After
    public void tearDown() throws Exception {
        connection.createStatement().executeUpdate("DROP TABLE account");
        connection.commit();
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
    public void update() {
        Account excepted = new Account();
        excepted.setId(1);
        excepted.setFirstName("Dima");
        excepted.setLastName("Andreev");
        excepted.setPatronymicName("Borisovich");
        excepted.setBirthDay(Date.valueOf("1978-02-13"));
        excepted.setHomeAddress("Msk");
        excepted.setWorkAddress("Msk");
        excepted.setEmail("elimental@bk.ru");
        excepted.setIcq("49224940");
        excepted.setSkype("elimental13");
        excepted.setAdditionalInfo("adfasdfasdf");
        accountDAO.update(excepted);
        Account actual = accountDAO.getById(1);
        assertEquals(excepted.toString(), actual.toString());
    }

    @Test
    public void delete() {
        accountDAO.delete(3);
        Account account = accountDAO.getById(3);
        assertNull(account);
    }
}