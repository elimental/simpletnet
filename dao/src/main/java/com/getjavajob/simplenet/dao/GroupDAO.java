package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

public class GroupDAO implements AbstractDAO<Group> {
    private static final String DELETE_BY_ID = "DELETE FROM groupp WHERE groupId = ?";
    private static final String INSERT_GROUP = "INSERT INTO groupp (groupName, groupOwner) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM groupp WHERE groupId = ?";
    private static final String SELECT_ALL = "SELECT * FROM groupp";
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
        PreparedStatement ps = connection.prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS);
        String groupName = group.getGroupName();
        if (groupName == null) {
            ps.setNull(1, VARCHAR);
        } else {
            ps.setString(1, groupName);
        }
        int groupOwner = group.getGroupOwner();
        if (groupOwner == 0) {
            ps.setNull(2, INTEGER);
        } else {
            ps.setInt(2, groupOwner);
        }
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

    private Group createGroupFromResult(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getInt("groupId"));
        group.setGroupName(rs.getString("groupName"));
        group.setGroupOwner(rs.getInt("groupOwner"));
        return group;
    }
}
