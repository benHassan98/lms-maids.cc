package com.library.librarymanagementsystem.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "book_id", nullable = false)
    private Long bookId;
    @Column(name = "patron_id", nullable = false)
    private Long patronId;
    @Column(name = "borrowing_date", nullable = false)
    private Instant borrowingDate;
    @Column(name = "return_date", nullable = false)
    private Instant returnDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public Instant getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Instant borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Instant getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Instant returnDate) {
        this.returnDate = returnDate;
    }
}
