package com.getjavajob.simplenet.dao;

import com.getjavajob.simplenet.common.entity.BaseEntity;

import java.util.List;

public abstract class AbstractDAO<T extends BaseEntity> {
    public abstract List<T> getAll();

    public abstract T getById(int id);

    public abstract int add(T t);

    public abstract void delete(int id);
}
