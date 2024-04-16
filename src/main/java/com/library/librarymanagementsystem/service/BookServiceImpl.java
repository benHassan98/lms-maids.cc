package com.library.librarymanagementsystem.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagementsystem.model.Book;
import jakarta.persistence.*;


import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements CRUDService<Book> {
    @Override
    @CachePut("books")
    public Long create(Book book) {

        EntityManager entityManager =
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(book);

        entityManager.getTransaction().commit();

        entityManager.close();

        return book.getId();
    }

    @Override
    @Cacheable("books")
    public Optional<Book> findById(Long id) {

        return Optional.ofNullable(
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager()
                        .find(Book.class, id)
        );
    }

    @Override
    public List<Book> findAll() {

        return Persistence
                .createEntityManagerFactory("library")
                .createEntityManager()
                .createNativeQuery("SELECT * FROM books", Book.class)
                .getResultList();
    }

    @Override
    @CachePut("books")
    public Optional<Book> update(Book book) {

        EntityManager entityManager =
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager();

        entityManager.getTransaction().begin();

        if(Objects.isNull(
                entityManager.find(Book.class, book.getId())
        )
        ){

            entityManager.getTransaction().commit();

            entityManager.close();

            return Optional.empty();
        }


        entityManager.merge(book);

        entityManager.getTransaction().commit();

        entityManager.close();


        return Optional.of(book);
    }

    @Override
    @CacheEvict("books")
    public void deleteById(Long id) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("library")
                .createEntityManager();

        entityManager.getTransaction().begin();

        Book book = entityManager.find(Book.class, id);

        if(Objects.nonNull(book)){
            entityManager.remove(book);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public boolean isUniqueIsbn(String isbn, Long id){

        List res;

        if(Objects.isNull(id)){
            res = Persistence
                    .createEntityManagerFactory("library")
                    .createEntityManager()
                    .createNativeQuery("SELECT id FROM books WHERE isbn=:isbn")
                    .setParameter("isbn", isbn)
                    .getResultList();
        } else{
            res = Persistence
                    .createEntityManagerFactory("library")
                    .createEntityManager()
                    .createNativeQuery("SELECT id FROM books WHERE isbn=:isbn AND id != :id")
                    .setParameter("isbn", isbn)
                    .setParameter("id", id)
                    .getResultList();
        }

        return res.size() == 0;

    }


}
