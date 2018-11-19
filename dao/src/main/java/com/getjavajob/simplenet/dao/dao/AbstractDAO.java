package com.getjavajob.simplenet.dao.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public class AbstractDAO<T extends Serializable> {
    // todo serializable
    // todo hot keys !!!

    @PersistenceContext
    EntityManager entityManager;

    @Getter
    @Setter
    private Class<T> clazz;

    public void add(T t) {
        entityManager.persist(t);
    }

    public T get(Long id) {
        return entityManager.find(clazz, id);
    }

    public void update(T t) {
        entityManager.merge(t);
    }

    public void delete(T t) {
        entityManager.remove(t);
    }

    public void deleteById(Long id) {
        T t = get(id);
        delete(t);
    }

    public List<T> getAll() {
        return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
    }
}
