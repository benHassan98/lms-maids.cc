package com.library.librarymanagementsystem.service;

public interface BorrowingRecordService {
    public boolean borrowBook(Long patronId, Long bookId);
    public boolean returnBook(Long patronId, Long bookId);
    public boolean checkIfEntitiesExist(Long patronId, Long bookId);
}
