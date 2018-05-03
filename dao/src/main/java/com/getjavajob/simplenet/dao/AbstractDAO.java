package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.entity.BaseEntity;

import java.util.List;

public interface AbstractDAO<T extends BaseEntity> {
    List<T> getAll();

    T getById(int id);

    void add(T account);

    void delete(int id);
}
