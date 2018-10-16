package com.getjavajob.simplenet.dao.dao;

import com.getjavajob.simplenet.common.entity.BaseEntity;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO<T extends BaseEntity> {

    List<T> getAll();

    T getById(int id);

    int add(T t) throws SQLException;

    void delete(int id) throws SQLException;
}
