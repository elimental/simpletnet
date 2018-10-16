package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.dao.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Message.*;

@Service
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Transactional
    public void sendWallMessage(int userId, String text) {
        sendMessage(userId, userId, text, WALL);
    }

    public List<Message> getWallMessages(int userId) {
        List<Message> messages = messageDAO.getWallMessages(userId);
        Collections.sort(messages);
        return messages;
    }

    @Transactional
    public void sendGroupMessage(int userId, int groupId, String text) {
        sendMessage(userId, groupId, text, GROUP);
    }

    public List<Message> getGroupMessages(int groupId) {
        List<Message> messages = messageDAO.getGroupMessages(groupId);
        Collections.sort(messages);
        return messages;
    }

    @Transactional
    public void sendPersonalMessage(int userFromId, int userToId, String text) {
        sendMessage(userFromId, userToId, text, PERSONAL);
    }

    private void sendMessage(int author, int destination, String text, int type) {
        Message message = new Message();
        message.setText(text);
        message.setCreateDate(new Timestamp(System.currentTimeMillis()));
        message.setAuthor(author);
        message.setType(type);
        message.setDestination(destination);
        messageDAO.add(message);
    }

    public List<Message> getChatMessages(int userId, int secondTalkerId) {
        return messageDAO.getChatMessages(userId, secondTalkerId);
    }

    public List<Integer> getTalkersId(int userId) {
        return messageDAO.getTalkersId(userId);
    }
}
