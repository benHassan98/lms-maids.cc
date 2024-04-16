package com.library.librarymanagementsystem;


import com.library.librarymanagementsystem.service.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BorrowingRecordTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BorrowingRecordServiceImpl borrowingRecordService;



    @Test
    public void borrowBook() throws Exception{

        when(borrowingRecordService.borrowBook(eq(2L), eq(1L))).thenReturn(true);
        when(borrowingRecordService.borrowBook(eq(3L), eq(2L))).thenReturn(false);

        mockMvc.perform(
                post("/api/borrow/1/patron/2")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(httpBasic("admin","maids.cc"))

        )
                .andExpect(status().isOk());

        mockMvc.perform(
                        post("/api/borrow/2/patron/3")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))

                )
                .andExpect(status().isBadRequest());

    }


    @Test
    public void returnBook() throws Exception{

        when(borrowingRecordService.returnBook(eq(2L), eq(1L))).thenReturn(true);
        when(borrowingRecordService.returnBook(eq(3L), eq(2L))).thenReturn(false);

        mockMvc.perform(
                        put("/api/return/1/patron/2")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))

                )
                .andExpect(status().isOk());

        mockMvc.perform(
                        put("/api/return/2/patron/3")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin","maids.cc"))

                )
                .andExpect(status().isBadRequest());

    }




}
