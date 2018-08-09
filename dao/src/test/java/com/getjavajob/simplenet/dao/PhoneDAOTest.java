package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Phone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.PhoneType.HOME;
import static com.getjavajob.simplenet.common.entity.PhoneType.WORK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhoneDAOTest {
    private static Connection connection;
    private static PhoneDAO phoneDAO;

    @Before
    public void setUp() throws Exception {
        this.connection = DBConnectionPool.getInstance().getConnection();
        connection.createStatement().executeUpdate("CREATE TABLE phone (" +
                "  number VARCHAR(45) NOT NULL, " +
                "  type ENUM('home', 'work') NOT NULL," +
                "  phoneOwner INT NOT NULL)");
        connection.createStatement().executeUpdate("INSERT INTO phone (number, type, phoneOwner)" +
                "  VALUES ('123456','home' ,1),('234567','work' ,1),('345678','work' ,1),('123456', 'home',2)," +
                "('234567', 'work', 2),('333444','home' ,2)");
        connection.commit();
        connection.close();
        phoneDAO = new PhoneDAO();
    }

    @After
    public void tearDown() throws Exception {
        connection.createStatement().executeUpdate("DROP TABLE phone");
        connection.commit();
        connection.close();
    }

    @Test
    public void getPhonesByOwnerId() {
        List<Phone> excepted = new ArrayList<>();
        Phone phone1 = new Phone();
        phone1.setNumber("123456");
        phone1.setType(HOME);
        phone1.setPhoneOwner(2);
        Phone phone2 = new Phone();
        phone2.setNumber("234567");
        phone2.setType(WORK);
        phone2.setPhoneOwner(2);
        Phone phone3 = new Phone();
        phone3.setNumber("333444");
        phone3.setType(HOME);
        phone3.setPhoneOwner(2);
        excepted.add(phone1);
        excepted.add(phone2);
        excepted.add(phone3);
        assertEquals(excepted, phoneDAO.getPhonesByOwnerId(2));

    }

    @Test
    public void add() {
        Phone excepted = new Phone();
        excepted.setNumber("777888");
        excepted.setType(HOME);
        excepted.setPhoneOwner(5);
        phoneDAO.add(excepted);
        Phone actual = phoneDAO.getPhonesByOwnerId(5).get(0);
        assertEquals(excepted, actual);
    }

    @Test
    public void deleteByOwnerId() {
        phoneDAO.deleteByOwnerId(1);
        List<Phone> excepted = phoneDAO.getPhonesByOwnerId(1);
        assertTrue(excepted.isEmpty());
    }
}