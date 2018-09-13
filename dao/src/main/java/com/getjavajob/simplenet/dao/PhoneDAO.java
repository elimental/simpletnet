package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.common.entity.PhoneType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.PhoneType.HOME;
import static com.getjavajob.simplenet.common.entity.PhoneType.WORK;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

public class PhoneDAO implements AbstractDAO<Phone> {
    private static final String DELETE_BY_OWNER_ID = "DELETE FROM phone WHERE phoneOwner = ?";
    private static final String INSERT_PHONE = "INSERT INTO phone (number, type, phoneOwner) VALUES (?, ?, ?)";
    private static final String SELECT_PHONES_BY_OWNER_ID = "SELECT * FROM phone WHERE phoneOwner = ?";
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    @Override
    public List<Phone> getAll() {
        return null;
    }

    @Override
    public Phone getById(int id) {
        return null;
    }

    public List<Phone> getPhonesByOwnerId(int ownerId) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_PHONES_BY_OWNER_ID);
            ps.setInt(1, ownerId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Phone> phones = new ArrayList<>();
                while (rs.next()) {
                    phones.add(createPhoneFromResult(rs));
                }
                return phones;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int add(Phone phone) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_PHONE);
        String number = phone.getNumber();
        if (number == null) {
            ps.setNull(1, VARCHAR);
        } else {
            ps.setString(1, number);
        }
        PhoneType phoneType = phone.getType();
        if (phoneType == null) {
            ps.setNull(2, INTEGER);
        } else {
            String type = null;
            if (phoneType == HOME) {
                type = "home";
            } else {
                type = "work";
            }
            ps.setString(2, type);
        }
        int phoneOwner = phone.getPhoneOwner();
        if (phoneOwner == 0) {
            ps.setNull(3, INTEGER);
        } else {
            ps.setInt(3, phoneOwner);
        }
        ps.executeUpdate();
        return 0;
    }


    public void deleteByOwnerId(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(DELETE_BY_OWNER_ID);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) {
    }

    private Phone createPhoneFromResult(ResultSet rs) throws SQLException {
        Phone phone = new Phone();
        phone.setNumber(rs.getString("number"));
        String phoneType = rs.getString("type");
        if (phoneType.equals("home")) {
            phone.setType(HOME);
        } else {
            phone.setType(WORK);
        }
        phone.setPhoneOwner(rs.getInt("phoneOwner"));
        return phone;
    }
}
