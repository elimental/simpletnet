package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.entity.Group;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GroupDAOTest {
    private static Connection connection;
    private static GroupDAO groupDAO;

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
            groupDAO = new GroupDAO(connection);
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
        List<Group> groups = groupDAO.getAll();
        assertEquals(3, groups.size());
    }

    @Test
    public void getById() {
        Group expected = new Group();
        expected.setId(2);
        expected.setGroupName("group2");
        expected.setGroupOwner(2);
        Group actual = groupDAO.getById(2);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void add() {
        Group excepted = new Group();
        excepted.setId(4);
        excepted.setGroupName("group4");
        excepted.setGroupOwner(3);
        groupDAO.add(excepted);
        Group actual = groupDAO.getById(4);
        assertEquals(excepted.toString(), actual.toString());
    }

    @Test
    public void delete() {
        groupDAO.delete(3);
        Group group = groupDAO.getById(3);
        assertNull(group);
    }
}