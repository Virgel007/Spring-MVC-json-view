package com.example.demo.service;

import java.util.List;

public interface CRUDServices<T> {
    T getById(Long id);

    List<T> getAll();

    void create(T item);

    void update(Long id, T item);

    void delete(Long id);
}