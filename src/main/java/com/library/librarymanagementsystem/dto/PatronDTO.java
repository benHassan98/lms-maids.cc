package com.library.librarymanagementsystem.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagementsystem.model.Patron;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatronDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    @NotNull
    @Size(min = 1, max = 80)
    private String contactInfo;



    public Patron getPatron(){


        Patron patron = new Patron();

        patron.setId(this.id);
        patron.setName(this.name);
        patron.setContactInfo(this.contactInfo);


        return patron;
    }



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

}
