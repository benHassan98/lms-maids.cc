package com.library.librarymanagementsystem.service;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {
    public Long create(T entity);
    public Optional<T> findById(Long id);
    public List<T> findAll();
    public Optional<T> update(T entity);
    public void deleteById(Long id);
}
