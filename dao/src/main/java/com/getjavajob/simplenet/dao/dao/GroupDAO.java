package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.List;

import static java.sql.Types.BLOB;

@Repository
public class GroupDAO implements AbstractDAO<Group> {

    private static final String DELETE_BY_ID = "DELETE FROM groups WHERE id = ?";
    private static final String INSERT_GROUP = "INSERT INTO groups (name, owner, picture, createDate, " +
            "description) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_GROUP = "UPDATE groups SET name = ?, picture = ?, " +
            "description = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM groups WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM groups";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new GroupMapper());
    }

    @Override
    public Group getById(int id) {
        List<Group> groups = jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, new GroupMapper());
        return groups.isEmpty() ? null : groups.get(0);
    }

    @Override
    public int add(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, group.getName());
            ps.setInt(2, group.getOwner());
            byte[] picture = group.getPicture();
            if (picture != null) {
                ps.setBlob(3, new ByteArrayInputStream(picture));
            } else {
                ps.setNull(3, BLOB);
            }
            ps.setDate(4, group.getCreateDate());
            ps.setString(5, group.getDescription());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(Group group) {
        jdbcTemplate.update(UPDATE_GROUP,
                group.getName(),
                group.getPicture(),
                group.getDescription(),
                group.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private static final class GroupMapper implements RowMapper<Group> {

        @Override
        public Group mapRow(ResultSet rs, int i) throws SQLException {
            Group group = new Group();
            group.setId(rs.getInt("id"));
            group.setName(rs.getString("name"));
            group.setOwner(rs.getInt("owner"));
            group.setPicture(rs.getBytes("picture"));
            group.setCreateDate(rs.getDate("createDate"));
            group.setDescription(rs.getString("description"));
            return group;
        }
    }
}
