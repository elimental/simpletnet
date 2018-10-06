package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Message.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MessageDAO implements AbstractDAO<Message> {
    private static final String SELECT_BY_ID = "SELECT FROM message WHERE id = ?";
    private static final String INSERT_MESSAGE = "INSERT INTO message (text, createDate, author, type, destination) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM message WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM message";
    private static final String SELECT_MESSAGES_BY_DEST_AND_TYPE = "SELECT * FROM message WHERE destination = ? AND " +
            "type = ?";
    private static final String SELECT_CHAT_MESSAGES = "SELECT * FROM message WHERE ((author = ? AND destination = ?)" +
            " OR (author = ? AND destination = ?)) AND type = ?";
    private static final String SELECT_TALKERS = "(SELECT destination FROM message WHERE author = ? AND type = ?) " +
            "UNION " +
            "(SELECT author FROM message WHERE destination = ? AND type = ?)";

    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(createMessageFromResult(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message getById(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createMessageFromResult(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int add(Message message) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE, RETURN_GENERATED_KEYS);
        String text = message.getText();
        ps.setString(1, text);
        Timestamp createDate = message.getCreateDate();
        ps.setTimestamp(2, createDate);
        int author = message.getAuthor();
        ps.setInt(3, author);
        int type = message.getType();
        ps.setInt(4, type);
        int destination = message.getDestination();
        ps.setInt(5, destination);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public List<Message> getWallMessages(int userId) {
        List<Message> messages = new ArrayList<>();
        getMessages(userId, WALL, messages);
        return messages;
    }

    public List<Message> getGroupMessages(int groupId) {
        List<Message> messages = new ArrayList<>();
        getMessages(groupId, GROUP, messages);
        return messages;
    }

    private void getMessages(int id, int type, List<Message> messages) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_MESSAGES_BY_DEST_AND_TYPE);
            ps.setInt(1, id);
            ps.setInt(2, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(createMessageFromResult(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getTalkersId(int userId) {
        List<Integer> talkersId = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_TALKERS);
            ps.setInt(1, userId);
            ps.setInt(2, PERSONAL);
            ps.setInt(3, userId);
            ps.setInt(4, PERSONAL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                talkersId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return talkersId;
    }

    public List<Message> getChatMessages(int userId, int secondTalkerId) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_CHAT_MESSAGES);
            ps.setInt(1, userId);
            ps.setInt(2, secondTalkerId);
            ps.setInt(3, secondTalkerId);
            ps.setInt(4, userId);
            ps.setInt(5, PERSONAL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(createMessageFromResult(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private Message createMessageFromResult(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt("id"));
        message.setText(rs.getString("text"));
        message.setCreateDate(rs.getTimestamp("createDate"));
        message.setAuthor(rs.getInt("author"));
        message.setType(rs.getInt("type"));
        message.setId(rs.getInt("destination"));
        return message;
    }
}
