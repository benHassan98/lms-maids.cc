package com.library.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patrons")
public class Patron {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;
    @ElementCollection
    @CollectionTable(name = "borrowing_records",joinColumns = @JoinColumn(name = "patron_id"))
    @Column(name = "book_id")
    @JsonIgnore
    private List<Long> bookList = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Long> getBookList() {
        return List.copyOf(bookList);
    }

}
