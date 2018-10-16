package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.dao.config.DAOConfig;
import com.getjavajob.simplenet.dao.config.DAOTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOConfig.class, DAOTestConfig.class})
@Sql(value = {"classpath:drop_db.sql", "classpath:create_db.sql", "classpath:populate_db.sql"},
        executionPhase = BEFORE_TEST_METHOD)
public class MessageDAOTest {

    @Autowired
    MessageDAO messageDAO;

    @Test
    public void getAll() {
        List<Message> expectedMessages = new ArrayList<>();
        Message expectedMessage1 = new Message();
        expectedMessage1.setId(1);
        expectedMessage1.setText("ни чего не происходит");
        expectedMessage1.setCreateDate(Timestamp.valueOf("2018-01-01 14:12:00"));
        expectedMessage1.setAuthor(1);
        expectedMessage1.setType(1);
        expectedMessage1.setDestination(1);
        expectedMessages.add(expectedMessage1);

        Message expectedMessage2 = new Message();
        expectedMessage2.setId(2);
        expectedMessage2.setText("хорошая группа");
        expectedMessage2.setCreateDate(Timestamp.valueOf("2018-02-01 12:00:00"));
        expectedMessage2.setAuthor(1);
        expectedMessage2.setType(2);
        expectedMessage2.setDestination(4);
        expectedMessages.add(expectedMessage2);

        Message expectedMessage3 = new Message();
        expectedMessage3.setId(3);
        expectedMessage3.setText("привет");
        expectedMessage3.setCreateDate(Timestamp.valueOf("2018-03-02 11:01:00"));
        expectedMessage3.setAuthor(1);
        expectedMessage3.setType(3);
        expectedMessage3.setDestination(4);
        expectedMessages.add(expectedMessage3);

        Message expectedMessage4 = new Message();
        expectedMessage4.setId(4);
        expectedMessage4.setText("здарова");
        expectedMessage4.setCreateDate(Timestamp.valueOf("2018-04-02 11:01:00"));
        expectedMessage4.setAuthor(2);
        expectedMessage4.setType(3);
        expectedMessage4.setDestination(1);
        expectedMessages.add(expectedMessage4);

        List<Message> actualMessages = messageDAO.getAll();
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void getById() {
        Message expected = new Message();
        expected.setId(2);
        expected.setText("хорошая группа");
        expected.setCreateDate(Timestamp.valueOf("2018-02-01 12:00:00"));
        expected.setAuthor(1);
        expected.setType(2);
        expected.setDestination(4);
        Message actual = messageDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void add() {
        Message expected = new Message();
        expected.setId(4);
        expected.setText("плохая группа");
        expected.setCreateDate(Timestamp.valueOf("2018-02-01 12:00:00"));
        expected.setAuthor(1);
        expected.setType(2);
        expected.setDestination(4);
        messageDAO.add(expected);
        Message actual = messageDAO.getById(4);
        assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        messageDAO.delete(2);
        Message deleted = messageDAO.getById(2);
        assertNull(deleted);
    }

    @Test
    public void getWallMessages() {
        List<Message> exceptWallMessages = new ArrayList<>();
        Message expected = new Message();
        expected.setId(1);
        expected.setText("ни чего не происходит");
        expected.setCreateDate(Timestamp.valueOf("2018-01-01 14:12:00"));
        expected.setAuthor(1);
        expected.setType(1);
        expected.setDestination(1);
        exceptWallMessages.add(expected);
        List<Message> actualWallMessasges = messageDAO.getWallMessages(1);
        assertEquals(exceptWallMessages, actualWallMessasges);
    }

    @Test
    public void getGroupMessages() {
        List<Message> expectGroupMessages = new ArrayList<>();
        Message expected = new Message();
        expected.setId(2);
        expected.setText("хорошая группа");
        expected.setCreateDate(Timestamp.valueOf("2018-02-01 12:00:00"));
        expected.setAuthor(1);
        expected.setType(2);
        expected.setDestination(4);
        expectGroupMessages.add(expected);
        List<Message> actualMessages = messageDAO.getGroupMessages(4);
        assertEquals(expectGroupMessages, actualMessages);
    }

    @Test
    public void getTalkersId() {
        List<Integer> expectedTalkersId = new ArrayList<>();
        Integer expected1 = 2;
        Integer expected2 = 4;
        expectedTalkersId.add(expected1);
        expectedTalkersId.add(expected2);
        List<Integer> actualTalkersId = messageDAO.getTalkersId(1);
        assertEquals(expectedTalkersId, actualTalkersId);
    }

    @Test
    public void getChatMessages() {
        List<Message> expectedMessages = new ArrayList<>();
        Message expected = new Message();
        expected.setId(4);
        expected.setText("здарова");
        expected.setCreateDate(Timestamp.valueOf("2018-04-02 11:01:00"));
        expected.setAuthor(2);
        expected.setType(3);
        expected.setDestination(1);
        expectedMessages.add(expected);
        List<Message> actualMessages = messageDAO.getChatMessages(1, 2);
        assertEquals(expectedMessages, actualMessages);
    }
}