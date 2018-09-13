package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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
                "  firstName VARCHAR(45) NULL," +
                "  lastName VARCHAR(45) NULL," +
                "  patronymicName VARCHAR(45) NULL," +
                "  birthDay DATE NULL," +
                "  email VARCHAR(45) NULL," +
                "  icq VARCHAR(45) NULL," +
                "  skype VARCHAR(45) NULL," +
                "  additionalInfo VARCHAR(1000) NULL," +
                "  passHash VARCHAR(200) NULL," +
                "  regDate DATE NULL," +
                "  photo VARCHAR(45) NULL," +
                "  PRIMARY KEY (userId)," +
                "  UNIQUE INDEX id_UNIQUE (userId ASC))");
        connection.createStatement().executeUpdate("INSERT INTO account (firstName, lastName, patronymicName, " +
                "birthDay, regDate, email, passHash, icq, skype, additionalInfo)" +
                "  VALUES ('Dima', 'Andreev', 'Borisovich', '1978-02-13', '2017-02-02', 'elimental@bk.ru'," +
                "  'DGFDGSDGDSGDGSWEWETR', '49224940', 'elimetal13', 'adfasdfasdf'), ('Vasya','Petrov'," +
                " 'Ivanovich', '1981-12-17', '2015-10-13', 'vasya@bk.ru', 'DSGFSDGF%@#%@#ASDFASFS', '49444940'," +
                " 'vasya17', 'adfasdfasdf'), ('Petya','Ivanov', 'Petrovich', '1970-06-21'," +
                " '2016-12-15', 'petya@bk.ru', 'ASDFASDAFD','43444940', 'petya21', 'adfasdfasdf')");
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
        expected.setRegDate(Date.valueOf("2015-10-13"));
        expected.setEmail("vasya@bk.ru");
        expected.setPassHash("DSGFSDGF%@#%@#ASDFASFS");
        expected.setIcq("49444940");
        expected.setSkype("vasya17");
        expected.setAdditionalInfo("adfasdfasdf");
        Account actual = accountDAO.getById(2);
        assertEquals(expected.toString(), actual.toString());

    }

 //   @Test
    public void add() throws SQLException {
        Account excepted = new Account();
        excepted.setId(4);
        excepted.setFirstName("Vasya");
        excepted.setLastName("Petrov");
        excepted.setPatronymicName("Ivanovich");
        excepted.setBirthDay(Date.valueOf("1981-12-17"));
        excepted.setRegDate(Date.valueOf("2015-01-15"));
        excepted.setEmail("vasya@bk.ru");
        excepted.setPassHash("GFSGF$%@#$%@#$%");
        excepted.setIcq("49444940");
        excepted.setSkype("vasya17");
        excepted.setAdditionalInfo("adfasdfasdf");
        accountDAO.add(excepted);
        Account actual = accountDAO.getById(4);
        assertEquals(excepted.toString(), actual.toString());
    }

  //  @Test
    public void update() throws SQLException {
        Account excepted = new Account();
        excepted.setId(1);
        excepted.setFirstName("Sergey");
        excepted.setLastName("Ivanov");
        excepted.setPatronymicName("Vladimirovich");
        excepted.setBirthDay(Date.valueOf("1978-02-13"));
        excepted.setRegDate(Date.valueOf("2017-02-02"));
        excepted.setEmail("elimental@bk.ru");
        excepted.setPassHash("DGFDGSDGDSGDGSWEWETR");
        excepted.setIcq("49224940");
        excepted.setSkype("elimental13");
        excepted.setAdditionalInfo("adfasdfasdf");
        accountDAO.update(excepted);
        Account actual = accountDAO.getById(1);
        assertEquals(excepted.toString(), actual.toString());
    }

    @Test
    public void delete() throws SQLException {
        accountDAO.delete(3);
        Account account = accountDAO.getById(3);
        assertNull(account);
    }

    @Test
    public void getByEmail() {
        Account expected = new Account();
        expected.setId(2);
        expected.setFirstName("Vasya");
        expected.setLastName("Petrov");
        expected.setPatronymicName("Ivanovich");
        expected.setBirthDay(Date.valueOf("1981-12-17"));
        expected.setRegDate(Date.valueOf("2015-10-13"));
        expected.setEmail("vasya@bk.ru");
        expected.setPassHash("DSGFSDGF%@#%@#ASDFASFS");
        expected.setIcq("49444940");
        expected.setSkype("vasya17");
        expected.setAdditionalInfo("adfasdfasdf");
        Account actual = accountDAO.getByEmail("vasya@bk.ru");
        assertEquals(expected.toString(), actual.toString());
    }
}