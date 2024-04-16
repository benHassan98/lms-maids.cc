package com.library.librarymanagementsystem.service;


import com.library.librarymanagementsystem.model.Patron;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class PatronServiceImpl implements CRUDService<Patron> {
    @Override
    @CachePut("patrons")
    public Long create(Patron patron) {
        EntityManager entityManager =
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(patron);

        entityManager.getTransaction().commit();

        entityManager.close();

        return patron.getId();
    }

    @Override
    @Cacheable("patrons")
    public Optional<Patron> findById(Long id) {
        return Optional.ofNullable(
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager()
                        .find(Patron.class, id)
        );
    }

    @Override
    public List<Patron> findAll() {
        return Persistence
                .createEntityManagerFactory("library")
                .createEntityManager()
                .createNativeQuery("SELECT * FROM patrons", Patron.class)
                .getResultList();
    }

    @Override
    @CachePut("patrons")
    public Optional<Patron> update(Patron patron) {

        EntityManager entityManager =
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager();

        entityManager.getTransaction().begin();

        if(Objects.isNull(
                entityManager.find(Patron.class, patron.getId())
        )
        ){

            entityManager.getTransaction().commit();

            entityManager.close();

            return Optional.empty();
        }


        entityManager.merge(patron);

        entityManager.getTransaction().commit();

        entityManager.close();


        return Optional.of(patron);
    }

    @Override
    @CacheEvict("patrons")
    public void deleteById(Long id) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("library")
                .createEntityManager();

        entityManager.getTransaction().begin();

        Patron patron = entityManager.find(Patron.class, id);

        if(Objects.nonNull(patron)){
            entityManager.remove(patron);
        }

        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
