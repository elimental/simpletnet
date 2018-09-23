package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Account;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.*;

public class AccountDAO implements AbstractDAO<Account> {
    private static final String DELETE_BY_ID = "DELETE FROM account WHERE id = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO account (firstName, lastName, patronymicName," +
            " birthDay, regDate, email, passHash, icq, skype, additionalInfo, photo, userRole) VALUES (?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM account WHERE email = ?";
    private static final String SELECT_ALL = "SELECT * FROM account";
    private static final String UPDATE_ACCOUNT = "UPDATE account SET firstName = ?, lastName = ?, patronymicName = ?," +
            " birthDay = ?, icq = ?, skype = ?, additionalInfo = ?, photo = ?, userRole = ? WHERE id = ?";
    private static final String UPDATE_USER_ROLE = "UPDATE account SET userRole = ? WHERE id = ?";
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    @Override
    public List<Account> getAll() {
        try (Connection connection = connectionPool.getConnection()) {
            ResultSet rs = connection.createStatement().executeQuery(SELECT_ALL);
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(createAccountFromResult(rs));
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getById(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createAccountFromResult(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int add(Account account) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
        String firstName = account.getFirstName();
        if (firstName == null) {
            ps.setNull(1, VARCHAR);
        } else {
            ps.setString(1, firstName);
        }
        String lastName = account.getLastName();
        if (lastName == null) {
            ps.setNull(2, VARCHAR);
        } else {
            ps.setString(2, lastName);
        }
        String patronymicName = account.getPatronymicName();
        if (patronymicName == null) {
            ps.setNull(3, VARCHAR);
        } else {
            ps.setString(3, patronymicName);
        }
        Date birthDay = account.getBirthDay();
        if (birthDay == null) {
            ps.setNull(4, DATE);
        } else {
            ps.setDate(4, birthDay);
        }
        Date regDate = account.getRegDate();
        if (regDate == null) {
            ps.setNull(5, DATE);
        } else {
            ps.setDate(5, regDate);
        }
        String email = account.getEmail();
        if (email == null) {
            ps.setNull(6, VARCHAR);
        } else {
            ps.setString(6, email);
        }
        String passHash = account.getPassHash();
        if (passHash == null) {
            ps.setNull(7, VARCHAR);
        } else {
            ps.setString(7, passHash);
        }
        String icq = account.getIcq();
        if (icq == null) {
            ps.setNull(8, VARCHAR);
        } else {
            ps.setString(8, icq);
        }
        String skype = account.getSkype();
        if (skype == null) {
            ps.setNull(9, VARCHAR);
        } else {
            ps.setString(9, skype);
        }
        String additionalInfo = account.getAdditionalInfo();
        if (additionalInfo == null) {
            ps.setNull(10, VARCHAR);
        } else {
            ps.setString(10, additionalInfo);
        }
        byte[] photo = account.getPhoto();
        if (photo == null) {
            ps.setNull(11, BLOB);
        } else {
            ps.setBlob(11, new ByteArrayInputStream(photo));

        }
        int role = account.getRole();
        ps.setInt(12, role);
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

    public void update(Account account) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT);
        int id = account.getId();
        ps.setInt(10, id);
        String firstName = account.getFirstName();
        if (firstName == null) {
            ps.setNull(1, VARCHAR);
        } else {
            ps.setString(1, firstName);
        }
        String lastName = account.getLastName();
        if (lastName == null) {
            ps.setNull(2, VARCHAR);
        } else {
            ps.setString(2, lastName);
        }
        String patronymicName = account.getPatronymicName();
        if (patronymicName == null) {
            ps.setNull(3, VARCHAR);
        } else {
            ps.setString(3, patronymicName);
        }
        Date birthDay = account.getBirthDay();
        if (birthDay == null) {
            ps.setNull(4, DATE);
        } else {
            ps.setDate(4, birthDay);
        }
        String icq = account.getIcq();
        if (icq == null) {
            ps.setNull(5, VARCHAR);
        } else {
            ps.setString(5, icq);
        }
        String skype = account.getSkype();
        if (skype == null) {
            ps.setNull(6, VARCHAR);
        } else {
            ps.setString(6, account.getSkype());
        }
        String additionalInfo = account.getAdditionalInfo();
        if (additionalInfo == null) {
            ps.setNull(7, VARCHAR);
        } else {
            ps.setString(7, additionalInfo);
        }
        byte[] photo = account.getPhoto();
        if (photo == null) {
            ps.setNull(8, BLOB);
        } else {
            ps.setBlob(8, new ByteArrayInputStream(photo));
        }
        int role = account.getRole();
        ps.setInt(9, role);
        ps.executeUpdate();
    }

    public Account getByEmail(String email) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL);
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createAccountFromResult(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserRole(int id, int roleId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE_USER_ROLE);
        ps.setInt(1, roleId);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    private Account createAccountFromResult(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setFirstName(rs.getString("firstName"));
        account.setLastName(rs.getString("lastName"));
        account.setPatronymicName(rs.getString("patronymicName"));
        account.setBirthDay(rs.getDate("birthDay"));
        account.setRegDate(rs.getDate("regDate"));
        account.setEmail(rs.getString("email"));
        account.setPassHash(rs.getString("passHash"));
        account.setIcq(rs.getString("icq"));
        account.setSkype(rs.getString("skype"));
        account.setAdditionalInfo(rs.getString("additionalInfo"));
        account.setPhoto(rs.getBytes("photo"));
        account.setRole(rs.getInt("userRole"));
        return account;
    }
}
