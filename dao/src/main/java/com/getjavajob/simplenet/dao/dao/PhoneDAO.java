package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PhoneDAO implements AbstractDAO<Phone> {

    private static final String DELETE_BY_OWNER_ID = "DELETE FROM phone WHERE owner = ?";
    private static final String INSERT_PHONE = "INSERT INTO phone (number, type, owner) VALUES (?, ?, ?)";
    private static final String SELECT_PHONES_BY_OWNER_ID = "SELECT * FROM phone WHERE owner = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Phone> getAll() {
        return null;
    }

    @Override
    public Phone getById(int id) {
        return null;
    }

    public List<Phone> getPhonesByOwnerId(int ownerId) {
        return jdbcTemplate.query(SELECT_PHONES_BY_OWNER_ID, new Object[]{ownerId}, new PhoneMapper());
    }

    @Override
    public int add(Phone phone) {
        jdbcTemplate.update(INSERT_PHONE, phone.getNumber(), phone.getType(), phone.getOwner());
        return 0;
    }


    public void deleteByOwnerId(int id) {
        jdbcTemplate.update(DELETE_BY_OWNER_ID, id);
    }

    @Override
    public void delete(int id) {
    }

    private static final class PhoneMapper implements RowMapper<Phone> {

        @Override
        public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
            Phone phone = new Phone();
            phone.setNumber(resultSet.getString("number"));
            phone.setType(resultSet.getInt("type"));
            phone.setOwner(resultSet.getInt("owner"));
            return phone;
        }
    }
}
