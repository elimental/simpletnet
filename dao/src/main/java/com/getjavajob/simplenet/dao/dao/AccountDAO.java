package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.BLOB;

@Repository
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Account> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new AccountMapper());
    }

    @Override
    public Account getById(int id) {
        List<Account> accounts = jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, new AccountMapper());
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    @Override
    public int add(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_ACCOUNT, RETURN_GENERATED_KEYS);
            ps.setString(1, account.getFirstName());
            ps.setString(2, account.getLastName());
            ps.setString(3, account.getPatronymicName());
            ps.setDate(4, account.getBirthDay());
            ps.setDate(5, account.getRegDate());
            ps.setString(6, account.getEmail());
            ps.setString(7, account.getPassHash());
            ps.setString(8, account.getIcq());
            ps.setString(9, account.getSkype());
            ps.setString(10, account.getAdditionalInfo());
            byte[] photo = account.getPhoto();
            if (photo != null) {
                ps.setBlob(11, new ByteArrayInputStream(photo));
            } else {
                ps.setNull(11, BLOB);
            }
            ps.setInt(12, account.getRole());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }


    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    public void update(Account account) {
        jdbcTemplate.update(UPDATE_ACCOUNT,
                account.getFirstName(),
                account.getLastName(),
                account.getPatronymicName(),
                account.getBirthDay(),
                account.getIcq(),
                account.getSkype(),
                account.getAdditionalInfo(),
                account.getPhoto(),
                account.getRole(),
                account.getId()
        );
    }


    public Account getByEmail(String email) {
        List<Account> accounts = jdbcTemplate.query(SELECT_BY_EMAIL, new Object[]{email}, new AccountMapper());
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    public void updateUserRole(int id, int roleId) {
        jdbcTemplate.update(UPDATE_USER_ROLE, id, roleId);
    }

    private static final class AccountMapper implements RowMapper<Account> {

        @Override
        public Account mapRow(ResultSet rs, int i) throws SQLException {
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
}
