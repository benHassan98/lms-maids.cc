package com.library.librarymanagementsystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.service.BookServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.*;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Equals;
import org.mockito.internal.verification.api.VerificationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class BookTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookServiceImpl bookService;


    @BeforeEach
    public void beforeEach(){
        when(bookService.isUniqueIsbn(anyString(), any())).thenReturn(true);
    }



    @Test
    public void findAll() throws Exception {

        Book book1 = new Book(), book2 = new Book(), book3 = new Book();

        book1.setId(1L);
        book2.setId(2L);
        book3.setId(3L);

        var bookList = List.of(
                book1,
                book2,
                book3
        );


        when(bookService.findAll()).thenReturn(bookList);


        MvcResult mvcResult = mockMvc.perform(
                get("/api/books")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin","maids.cc")
                        )

                )
                .andReturn();

        List<Book> books = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });


        assertEquals(books.get(0).getId(), bookList.get(0).getId());
        assertEquals(books.get(1).getId(), bookList.get(1).getId());
        assertEquals(books.get(2).getId(), bookList.get(2).getId());

    }


    @Test
    public void findById() throws Exception {


        when(bookService.findById(eq(1L))).thenReturn(Optional.empty());
        when(bookService.findById(eq(2L))).thenReturn(Optional.of(new Book()));

        mockMvc.perform(
                get("/api/books/1")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin","maids.cc")
                        )

        )
                .andExpect(status().isNotFound());


        mockMvc.perform(
                get("/api/books/2")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin","maids.cc")
                        )

        )
                .andExpect(status().isOk());

    }

    @Test
    public void create() throws Exception{

        when(bookService.create(any(Book.class))).thenReturn(1L);

        mockMvc.perform(
                        post("/api/books")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("title","title")
                                .queryParam("author","author")
                                .queryParam("isbn","1234")
                )
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/books")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("title","title")
                                .queryParam("author","author")
                                .queryParam("isbn","1234")
                                .queryParam("publicationYear","2000")
                )
                .andExpect(status().isOk());

    }


    @Test
    public void update() throws Exception{

        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setIsbn("1234");
        book.setPublicationYear(2000);

        when(bookService.update(argThat(arg-> arg.equals(book)))).thenReturn(Optional.of(book));


        mockMvc.perform(
                        put("/api/books/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("title","title")
                                .queryParam("author","author")
                                .queryParam("isbn","1234")
                )
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        put("/api/books/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("title","title")
                                .queryParam("author","author")
                                .queryParam("isbn","1234")
                                .queryParam("publicationYear","2000")
                )
                .andExpect(status().isOk());

    }

    @Test
    public void deleteById() throws Exception{


        doNothing().when(bookService).deleteById(anyLong());


        mockMvc.perform(
                        delete("/api/books/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                )
                .andExpect(status().isOk());

        verify(bookService, atMostOnce()).deleteById(anyLong());

    }



}
