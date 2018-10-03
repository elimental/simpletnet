package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Group;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.*;

public class GroupDAO implements AbstractDAO<Group> {
    private static final String DELETE_BY_ID = "DELETE FROM groups WHERE id = ?";
    private static final String INSERT_GROUP = "INSERT INTO groups (name, owner, picture, createDate, " +
            "description) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_GROUP = "UPDATE groups SET name = ?, picture = ?, " +
            "description = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM groups WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM groups";

    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    @Override
    public List<Group> getAll() {
        try (Connection connection = connectionPool.getConnection()) {
            ResultSet rs = connection.createStatement().executeQuery(SELECT_ALL);
            List<Group> groups = new ArrayList<>();
            while (rs.next()) {
                groups.add(createGroupFromResult(rs));
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Group getById(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createGroupFromResult(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int add(Group group) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_GROUP, RETURN_GENERATED_KEYS);
        String name = group.getName();
        if (name == null) {
            ps.setNull(1, VARCHAR);
        } else {
            ps.setString(1, name);
        }
        int owner = group.getOwner();
        if (owner == 0) {
            ps.setNull(2, INTEGER);
        } else {
            ps.setInt(2, owner);
        }
        byte[] picture = group.getPicture();
        if (picture == null) {
            ps.setNull(3, BLOB);
        } else {
            ps.setBlob(3, new ByteArrayInputStream(picture));
        }
        Date createDate = group.getCreateDate();
        if (createDate == null) {
            ps.setNull(4, DATE);
        } else {
            ps.setDate(4, createDate);
        }
        String description = group.getDescription();
        if (description == null) {
            ps.setNull(5, VARCHAR);
        } else {
            ps.setString(5, description);
        }
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    public void update(Group group) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE_GROUP);
        int id = group.getId();
        ps.setInt(4, id);
        String groupName = group.getName();
        if (groupName == null) {
            ps.setNull(1, VARCHAR);
        } else {
            ps.setString(1, groupName);
        }
        byte[] picture = group.getPicture();
        if (picture == null) {
            ps.setNull(2, BLOB);
        } else {
            ps.setBlob(2, new ByteArrayInputStream(picture));
        }
        String description = group.getDescription();
        if (description == null) {
            ps.setNull(3, VARCHAR);
        } else {
            ps.setString(3, description);
        }
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    private Group createGroupFromResult(ResultSet rs) throws SQLException {
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
