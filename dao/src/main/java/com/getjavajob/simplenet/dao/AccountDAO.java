package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnectionPool;
import com.getjavajob.simplenet.common.entity.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.VARCHAR;

public class AccountDAO extends AbstractDAO<Account> {
    private static final String DELETE_BY_ID = "DELETE FROM account WHERE userId = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO account (firstName, lastName, patronymicName," +
            " birthDay, homeAddress, workAddress, email, icq, skype, additionalInfo) VALUES (?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM account WHERE userId = ?";
    private static final String SELECT_ALL = "SELECT * FROM account";
    private static final String UPDATE_ACCOUNT = "UPDATE account SET firstName = ?, lastName = ?, patronymicName = ?," +
            " birthDay = ?, homeAddress = ?, workAddress = ?, email = ?, icq = ?, skype = ?, additionalInfo = ?" +
            " WHERE userID = ?";
    private DBConnectionPool connectionPool;
    private Connection rollback;

    public AccountDAO() {
        this.connectionPool = DBConnectionPool.getInstance();
    }

    @Override
    public List<Account> getAll() {
        try (Connection connection = connectionPool.getConnection();
             ResultSet rs = connection.createStatement().executeQuery(SELECT_ALL)) {
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(createAccountFromResult(rs));
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account getById(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createAccountFromResult(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public int add(Account account) {
        int generatedKey = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            this.rollback = connection;
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
                ps.setNull(4, VARCHAR);
            } else {
                ps.setDate(4, birthDay);
            }
            String homeAddress = account.getHomeAddress();
            if (homeAddress == null) {
                ps.setNull(5, VARCHAR);
            } else {
                ps.setString(5, homeAddress);
            }
            String workAddress = account.getWorkAddress();
            if (workAddress == null) {
                ps.setNull(6, VARCHAR);
            } else {
                ps.setString(6, workAddress);
            }
            ps.setString(7, account.getEmail());
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
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            generatedKey = rs.getInt(1);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.rollback.rollback();
                this.rollback = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return generatedKey;
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            this.rollback = connection;
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.rollback.rollback();
                this.rollback = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Account account) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT)) {
            this.rollback = connection;
            ps.setInt(11, account.getId());
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
                ps.setNull(4, VARCHAR);
            } else {
                ps.setDate(4, birthDay);
            }
            String homeAddress = account.getHomeAddress();
            if (homeAddress == null) {
                ps.setNull(5, VARCHAR);
            } else {
                ps.setString(5, homeAddress);
            }
            String workAddress = account.getWorkAddress();
            if (workAddress == null) {
                ps.setNull(6, VARCHAR);
            } else {
                ps.setString(6, workAddress);
            }
            ps.setString(7, account.getEmail());
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
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.rollback.rollback();
                this.rollback = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private Account createAccountFromResult(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("userId"));
        account.setFirstName(rs.getString("firstName"));
        account.setLastName(rs.getString("lastName"));
        account.setPatronymicName(rs.getString("patronymicName"));
        account.setBirthDay(rs.getDate("birthDay"));
        account.setHomeAddress(rs.getString("homeAddress"));
        account.setWorkAddress(rs.getString("workAddress"));
        account.setEmail(rs.getString("email"));
        account.setIcq(rs.getString("icq"));
        account.setSkype(rs.getString("skype"));
        account.setAdditionalInfo(rs.getString("additionalInfo"));
        return account;
    }
}
