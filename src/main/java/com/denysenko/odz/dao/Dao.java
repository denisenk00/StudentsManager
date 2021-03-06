package com.denysenko.odz.dao;

import java.util.List;

public interface Dao<T> {
    void create(T t);
    void update(T t);
    void delete(T t);
    T get(int id);
    List<T> getAll();
}
