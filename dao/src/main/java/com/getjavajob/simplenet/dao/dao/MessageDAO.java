package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Message.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class MessageDAO implements AbstractDAO<Message> {

    private static final String SELECT_BY_ID = "SELECT * FROM message WHERE id = ?";
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

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Message> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new MessageMapper());
    }

    @Override
    public Message getById(int id) {
        List<Message> messages = jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, new MessageMapper());
        return messages.isEmpty() ? null : messages.get(0);
    }

    @Override
    public int add(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE, RETURN_GENERATED_KEYS);
            ps.setString(1, message.getText());
            ps.setTimestamp(2, message.getCreateDate());
            ps.setInt(3, message.getAuthor());
            ps.setInt(4, message.getType());
            ps.setInt(5, message.getDestination());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    public List<Message> getWallMessages(int userId) {
        return getMessages(userId, WALL);
    }

    public List<Message> getGroupMessages(int groupId) {
        return getMessages(groupId, GROUP);
    }

    private List<Message> getMessages(int id, int type) {
        return jdbcTemplate.query(SELECT_MESSAGES_BY_DEST_AND_TYPE, new Object[]{id, type}, new MessageMapper());
    }

    public List<Integer> getTalkersId(int userId) {
        return jdbcTemplate.query(SELECT_TALKERS, new Object[]{userId, PERSONAL, userId, PERSONAL},
                (resultSet, i) -> resultSet.getInt(1));
    }

    public List<Message> getChatMessages(int userId, int secondTalkerId) {
        return jdbcTemplate.query(SELECT_CHAT_MESSAGES, new Object[]{userId, secondTalkerId, secondTalkerId, userId,
                PERSONAL}, new MessageMapper());
    }

    private static final class MessageMapper implements RowMapper<Message> {

        @Override
        public Message mapRow(ResultSet rs, int i) throws SQLException {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setText(rs.getString("text"));
            message.setCreateDate(rs.getTimestamp("createDate"));
            message.setAuthor(rs.getInt("author"));
            message.setType(rs.getInt("type"));
            message.setDestination(rs.getInt("destination"));
            return message;
        }
    }
}
