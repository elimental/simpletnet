package com.getjavajob.simplenet.dao.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class AbstractDAO<T> implements Serializable {

    @Autowired
    protected SessionFactory sessionFactory;
    private Class<T> clazz;

    void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void add(T t) {
        sessionFactory.getCurrentSession().save(t);
    }

    public T get(Long id) {
        return sessionFactory.getCurrentSession().get(clazz, id);
    }

    public void update(T t) {
        sessionFactory.getCurrentSession().merge(t);
    }

    public void delete(T t) {
        sessionFactory.getCurrentSession().delete(t);
    }

    public void deleteById(Long id) {
        T t = get(id);
        delete(t);
    }

    @SuppressWarnings(value = "unchecked")
    public List<T> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from " + clazz.getName()).list();
    }
}
