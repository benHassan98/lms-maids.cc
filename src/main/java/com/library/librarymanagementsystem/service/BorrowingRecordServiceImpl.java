package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.model.BorrowingRecord;
import com.library.librarymanagementsystem.model.Patron;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class BorrowingRecordServiceImpl implements BorrowingRecordService{
    @Override
    public boolean borrowBook(Long patronId, Long bookId) {

        if(!this.checkIfEntitiesExist(patronId, bookId)){
            return false;
        }


        EntityManager entityManager =
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager();

        entityManager.getTransaction().begin();

        Book book = entityManager.find(Book.class, bookId);

        if(book.getBorrowed()){
            entityManager.getTransaction().commit();

            entityManager.close();

            return false;
        }

        book.setBorrowed(true);

        BorrowingRecord borrowingRecord = new BorrowingRecord();

        borrowingRecord.setBookId(bookId);
        borrowingRecord.setPatronId(patronId);
        borrowingRecord.setBorrowingDate(Instant.now());


        entityManager.persist(borrowingRecord);

        entityManager.merge(book);

        entityManager.getTransaction().commit();

        entityManager.close();

        return true;
    }

    @Override
    public boolean returnBook(Long patronId, Long bookId) {

        if(!this.checkIfEntitiesExist(patronId, bookId)){
            return false;
        }

        EntityManager entityManager =
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager();

        entityManager.getTransaction().begin();

        Book book = entityManager.find(Book.class, bookId);

        if(!book.getBorrowed()){

            entityManager.getTransaction().commit();

            entityManager.close();

            return false;
        }

        BorrowingRecord borrowingRecord = (BorrowingRecord) entityManager
                .createNativeQuery("SELECT * FROM borrowing_records WHERE patron_id = :patron_id AND book_id = :book_id AND return_date IS NULL ", BorrowingRecord.class)
                .setParameter("patron_id", patronId)
                .setParameter("book_id", bookId)
                .getSingleResult();


        borrowingRecord.setReturnDate(Instant.now());

        book.setBorrowed(false);

        entityManager.merge(borrowingRecord);

        entityManager.merge(book);

        entityManager.getTransaction().commit();

        entityManager.close();

        return true;
    }

    @Override
    public boolean checkIfEntitiesExist(Long patronId, Long bookId) {

        EntityManager entityManager =
                Persistence
                        .createEntityManagerFactory("library")
                        .createEntityManager();

        Patron patron = entityManager.find(Patron.class, patronId);
        Book book = entityManager.find(Book.class, bookId);

        return Objects.nonNull(patron) && Objects.nonNull(book);
    }





}
