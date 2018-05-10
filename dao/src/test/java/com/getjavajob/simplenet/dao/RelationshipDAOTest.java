package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RelationshipDAOTest {
    private static Connection connection;
    private static RelationshipDAO relationshipDAO;

    @Before
    public void setUp() throws Exception {
        this.connection = DBConnectionPool.getInstance().getConnection();
        connection.createStatement().executeUpdate("CREATE TABLE relationship (\n" +
                " userOneId INT NOT NULL,\n" +
                " userTwoId INT NOT NULL)");
        connection.createStatement().executeUpdate("INSERT INTO relationship (userOneId, userTwoId)\n" +
                "  VALUES (1,2),(1,3),(1,4),(2,3),(2,4),(3,5)");
        connection.commit();
        connection.close();
        relationshipDAO = new RelationshipDAO();
    }

    @After
    public void tearDown() throws Exception {
        connection.createStatement().executeUpdate("DROP TABLE relationship");
        connection.commit();
        connection.close();
    }

    @Test
    public void add() {
        relationshipDAO.add(1, 7);
        List<Integer> exepted = relationshipDAO.getAll(1);
        assertTrue(exepted.contains(7));
    }

    @Test
    public void delete() {
        relationshipDAO.delete(1, 3);
        List<Integer> exepted = relationshipDAO.getAll(1);
        assertTrue(!exepted.contains(3));
    }

    @Test
    public void getAll() {
        List<Integer> exepted = relationshipDAO.getAll(2);
        assertTrue(exepted.contains(3) && exepted.contains(4));
    }
}