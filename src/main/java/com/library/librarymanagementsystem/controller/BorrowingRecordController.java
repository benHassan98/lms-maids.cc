package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId){

        return borrowingRecordService.borrowBook(patronId, bookId) ?
                ResponseEntity.ok().build():
                ResponseEntity.badRequest().body("Please check if book exists and is not borrowed");

    }

    @PutMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){

        return borrowingRecordService.returnBook(patronId, bookId) ?
                ResponseEntity.ok().build():
                ResponseEntity.badRequest().body("Please check if book exists and is borrowed by given User");

    }


}
