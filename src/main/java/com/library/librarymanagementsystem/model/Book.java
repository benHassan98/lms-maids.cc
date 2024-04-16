package com.library.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    @Column(name = "is_borrowed", nullable = false)
    private Boolean borrowed = false;
    @ElementCollection
    @CollectionTable(name = "borrowing_records",joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "patron_id")
    private List<Long> patronIdList = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public List<Long> getPatronList() {
        return List.copyOf(patronIdList);
    }


    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Book book){
            return Objects.equals(this.id, book.getId());
        }
        return false;
    }
}
