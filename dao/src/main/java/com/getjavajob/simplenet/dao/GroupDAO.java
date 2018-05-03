package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnector;
import com.getjavajob.simplenet.entity.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

public class GroupDAO implements AbstractDAO<Group> {
    private static final String DELETE_BY_ID = "DELETE FROM groupp WHERE groupId = ?";
    private static final String INSERT_GROUP = "INSERT INTO groupp (groupName, groupOwner) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM groupp WHERE groupId = ?";
    private static final String SELECT_ALL = "SELECT * FROM groupp";
    private Connection connection;

    public GroupDAO() {
        this.connection = DBConnector.getConnection();
    }

    public GroupDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Group> getAll() {
        try (ResultSet rs = this.connection.createStatement().executeQuery(SELECT_ALL)) {
            List<Group> groups = new ArrayList<>();
            while (rs.next()) {
                groups.add(createGroupFromResult(rs));
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Group getById(int id) {
        try (PreparedStatement ps = this.connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createGroupFromResult(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public void add(Group group) {
        try (PreparedStatement ps = this.connection.prepareStatement(INSERT_GROUP)) {
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
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = this.connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Group createGroupFromResult(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getInt("groupId"));
        group.setGroupName(rs.getString("groupName"));
        group.setGroupOwner(rs.getInt("groupOwner"));
        return group;
    }
}
