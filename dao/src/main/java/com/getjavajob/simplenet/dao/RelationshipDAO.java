package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDAO {
    private static final String INSERT_RELATION = "INSERT INTO relationship (userOneId, userTwoId) VALUES (?, ?)";
    private static final String DELETE_RELATION = "DELETE FROM relationship WHERE (userOneId = ? AND userTwoId = ?) " +
            "OR ((userOneId = ? AND userTwoId = ?))";
    private static final String SELECT_RELATION = "SELECT userTwoId FROM relationship WHERE userOneId = ? " +
            "UNION SELECT userOneId FROM relationship WHERE userTwoId = ?";
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    public void add(int Id1, int Id2) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_RELATION);
        ps.setInt(1, Id1);
        ps.setInt(2, Id2);
        ps.executeUpdate();
    }

    public void delete(int Id1, int Id2) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_RELATION);
        ps.setInt(1, Id1);
        ps.setInt(2, Id2);
        ps.setInt(3, Id2);
        ps.setInt(4, Id1);
        ps.executeUpdate();
    }

    public List<Integer> getAll(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_RELATION);
            ps.setInt(1, id);
            ps.setInt(2, id);
            List<Integer> friends = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                friends.add(rs.getInt(1));
            }
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
