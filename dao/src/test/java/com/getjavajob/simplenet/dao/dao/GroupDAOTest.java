package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Group;
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
public class GroupDAOTest {

    @Autowired
    private GroupDAO groupDAO;

    @Test
    public void getAll() {
        List<Group> expectedGroups = new ArrayList<>();
        Group expected = new Group();
        expected.setId(1);
        expected.setName("group1");
        expected.setOwner(1);
        expected.setCreateDate(Date.valueOf("2018-01-01"));
        expected.setDescription("group1_description");
        expectedGroups.add(expected);

        Group expected2 = new Group();
        expected2.setId(2);
        expected2.setName("group2");
        expected2.setOwner(2);
        expected2.setCreateDate(Date.valueOf("2018-02-01"));
        expected2.setDescription("group2_description");
        expectedGroups.add(expected2);
        List<Group> actualGroups = groupDAO.getAll();
        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void getById() {
        Group expected = new Group();
        expected.setId(2);
        expected.setName("group2");
        expected.setOwner(2);
        expected.setCreateDate(Date.valueOf("2018-02-01"));
        expected.setDescription("group2_description");
        Group actual = groupDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void add() {
        Group expected = new Group();
        expected.setId(3);
        expected.setName("group3");
        expected.setOwner(3);
        expected.setCreateDate(Date.valueOf("2018-03-01"));
        expected.setDescription("group3_description");
        groupDAO.add(expected);
        Group actual = groupDAO.getById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Group expected = new Group();
        expected.setId(2);
        expected.setName("group2222222");
        expected.setOwner(2);
        expected.setCreateDate(Date.valueOf("2018-02-01"));
        expected.setDescription("group2_description");
        groupDAO.update(expected);
        Group actual = groupDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        groupDAO.delete(1);
        Group group = groupDAO.getById(1);
        assertNull(group);
    }
}