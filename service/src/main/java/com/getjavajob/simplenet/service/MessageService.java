package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.dao.MessageDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Message.*;

public class MessageService {
    private static MessageService ourInstance = new MessageService();
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private MessageService() {
    }

    public static MessageService getInstance() {
        return ourInstance;
    }


    public void sendWallMessage(int userId, String text) {
        sendMessage(userId, userId, text, WALL);
    }

    public List<Message> getWallMessages(int userId) {
        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getWallMessages(userId);
        Collections.sort(messages);
        return messages;
    }

    public void sendGroupMessage(int userId, int groupId, String text) {
        sendMessage(userId, groupId, text, GROUP);
    }

    public List<Message> getGroupMessages(int groupId) {
        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getGroupMessages(groupId);
        Collections.sort(messages);
        return messages;
    }

    public void sendPersonalMessage(int userFromId, int userToId, String text) {
        sendMessage(userFromId, userToId, text, PERSONAL);
    }

    private void sendMessage(int author, int destination, String text, int type) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            MessageDAO messageDAO = new MessageDAO();
            Message message = new Message();
            message.setText(text);
            message.setCreateDate(new Timestamp(System.currentTimeMillis()));
            message.setAuthor(author);
            message.setType(type);
            message.setDestination(destination);
            messageDAO.add(message);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Message> getChatMessages(int userId, int secondTalkerId) {
        MessageDAO messageDAO = new MessageDAO();
        return messageDAO.getChatMessages(userId, secondTalkerId);
    }

    public List<Integer> getTalkersId(int userId) {
        MessageDAO messageDAO = new MessageDAO();
        return messageDAO.getTalkersId(userId);
    }
}
