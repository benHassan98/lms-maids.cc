package com.library.librarymanagementsystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.service.BookServiceImpl;
import com.library.librarymanagementsystem.service.PatronServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atMostOnce;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatronTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatronServiceImpl patronService;


    @Test
    public void findAll() throws Exception{

        Patron patron1 = new Patron(), patron2 = new Patron(), patron3 = new Patron();

        patron1.setId(1L);
        patron2.setId(2L);
        patron3.setId(3L);

        var patronList = List.of(
                patron1,
                patron2,
                patron3
        );


        when(patronService.findAll()).thenReturn(patronList);

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/patrons")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))

                )
                .andReturn();

        List<Patron> patrons = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(patrons.get(0).getId(), patronList.get(0).getId());
        assertEquals(patrons.get(1).getId(), patronList.get(1).getId());
        assertEquals(patrons.get(2).getId(), patronList.get(2).getId());


    }

    @Test
    public void findById() throws Exception {


        when(patronService.findById(eq(1L))).thenReturn(Optional.empty());
        when(patronService.findById(eq(2L))).thenReturn(Optional.of(new Patron()));

        mockMvc.perform(
                        get("/api/patrons/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))

                )
                .andExpect(status().isNotFound());


        mockMvc.perform(
                        get("/api/patrons/2")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))

                )
                .andExpect(status().isOk());

    }

    @Test
    public void create() throws Exception{

        when(patronService.create(any(Patron.class))).thenReturn(1L);

        mockMvc.perform(
                        post("/api/patrons")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("name","name")
                )
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/patrons")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("name","name")
                                .queryParam("contactInfo","contactInfo")
                )
                .andExpect(status().isOk());

    }



    @Test
    public void update() throws Exception{

        Patron patron = new Patron();
        patron.setId(1L);


        when(patronService.update(argThat(arg-> arg.getId().equals(patron.getId())))).thenReturn(Optional.of(patron));


        mockMvc.perform(
                        put("/api/patrons/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("name","name")
                )
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        put("/api/patrons/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                                .queryParam("name","newName")
                                .queryParam("contactInfo","contactInfo")
                )
                .andExpect(status().isOk());

    }

    @Test
    public void deleteById() throws Exception{

        doNothing().when(patronService).deleteById(anyLong());


        mockMvc.perform(
                        delete("/api/patrons/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))
                )
                .andExpect(status().isOk());

        verify(patronService, atMostOnce()).deleteById(anyLong());

    }






}
