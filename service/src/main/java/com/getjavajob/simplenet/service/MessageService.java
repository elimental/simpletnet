package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.dao.MessageDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Message.WALL;

public class MessageService {
    private static MessageService ourInstance = new MessageService();
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private MessageService(){
    }

    public static MessageService getInstance() {
        return ourInstance;
    }


    public void sendWallMessage(int userId, String text) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            MessageDAO messageDAO = new MessageDAO();
            Message message = new Message();
            message.setText(text);
            message.setCreateDate(new Timestamp(System.currentTimeMillis()));
            message.setAuthor(userId);
            message.setType(WALL);
            message.setDestination(userId);
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

    public List<Message> getWallMessages(int userId) {
        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getWallMessages(userId);
        Collections.sort(messages);
        return messages;
    }
}
