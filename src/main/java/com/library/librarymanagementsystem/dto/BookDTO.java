package com.library.librarymanagementsystem.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagementsystem.model.Book;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDTO {


    private Long id;

    @NotNull
    @Size(min = 1, max = 280)
    private String title;

    @NotNull
    @Size(min = 2, max = 50)
    private String author;

    @NotNull
    @Min(1000)
    @Max(2500)
    private Integer publicationYear;

    @NotNull
    @Size(min = 1, max = 50)
    private String isbn;

    private Boolean borrowed;


    public Book getBook() {

        Book book = new Book();

        book.setId(this.id);
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setIsbn(this.isbn);
        book.setPublicationYear(this.publicationYear);
        book.setBorrowed(Objects.isNull(this.borrowed)?false:this.borrowed);



        return book;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

}
