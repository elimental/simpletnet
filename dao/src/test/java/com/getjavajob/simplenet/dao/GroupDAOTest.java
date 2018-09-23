package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Group;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GroupDAOTest {
    private static Connection connection;
    private static GroupDAO groupDAO;

    @Before
    public void setUp() throws Exception {
        this.connection = DBConnectionPool.getInstance().getConnection();
        connection.createStatement().executeUpdate("CREATE TABLE groups (" +
                "  id INT NOT NULL AUTO_INCREMENT," +
                "  name VARCHAR(45) NULL," +
                "  owner INT NULL," +
                "  picture LONGBLOB NULL," +
                "  createDate DATE NULL," +
                "  description VARCHAR(1000) NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE INDEX groupId_UNIQUE (id ASC))");
        connection.createStatement().executeUpdate("INSERT INTO groups (name, owner, " +
                "description)" +
                "  VALUES ('group1', 1, 'group1'),('group2', 2, 'group2'),('group3', 3, 'group3')");
        connection.commit();
        connection.close();
        groupDAO = new GroupDAO();
    }

    @After
    public void tearDown() throws Exception {
        connection.createStatement().executeUpdate("DROP TABLE groups");
        connection.commit();
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
        expected.setName("group2");
        expected.setOwner(2);
        expected.setDescription("group2");
        Group actual = groupDAO.getById(2);
        assertEquals(expected.toString(), actual.toString());
    }

    //  @Test
    public void add() throws SQLException {
        Group excepted = new Group();
        excepted.setId(4);
        excepted.setName("group4");
        excepted.setOwner(3);
        groupDAO.add(excepted);
        Group actual = groupDAO.getById(4);
        assertEquals(excepted.toString(), actual.toString());
    }

    @Test
    public void delete() throws SQLException {
        groupDAO.delete(3);
        Group group = groupDAO.getById(3);
        assertNull(group);
    }
}