package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.DBConnector;
import com.getjavajob.simplenet.entity.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.VARCHAR;

public class AccountDAO implements AbstractDAO<Account> {
    private static final String DELETE_BY_ID = "DELETE FROM account WHERE userId = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO account (firstName, lastName, patronymicName," +
            " birthDay, homeAddress, workAddress, email, icq, skype, additionalInfo) VALUES (?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM account WHERE userId = ?";
    private static final String SELECT_ALL = "SELECT * FROM account";
    private Connection connection;

    public AccountDAO() {
        this.connection = DBConnector.getConnection();
    }

    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> getAll() {
        try (ResultSet rs = this.connection.createStatement().executeQuery(SELECT_ALL)) {
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
        try (PreparedStatement ps = this.connection.prepareStatement(SELECT_BY_ID)) {
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
    public void add(Account account) {
        try (PreparedStatement ps = this.connection.prepareStatement(INSERT_ACCOUNT)) {
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
