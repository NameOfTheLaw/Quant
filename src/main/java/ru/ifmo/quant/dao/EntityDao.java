package ru.ifmo.quant.dao;

/**
 * Created by andrey on 05.11.2016.
 */
public interface EntityDao<T> {

    boolean exists(Long id);
    T save(T entity);
    void delete(T entity);

}
