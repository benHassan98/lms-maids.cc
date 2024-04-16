package com.library.librarymanagementsystem.controller;



import com.library.librarymanagementsystem.dto.BookDTO;
import com.library.librarymanagementsystem.service.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/")
public class BookController {

    private final BookServiceImpl bookService;
    @Autowired
    public BookController(BookServiceImpl bookService){
        this.bookService = bookService;
    }

    @GetMapping("books")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("books/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("books")
    public ResponseEntity<?> create(@Valid @ModelAttribute BookDTO bookDTO,
                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var book = bookDTO.getBook();

        if(!bookService.isUniqueIsbn(book.getIsbn(), null)){
            return ResponseEntity.badRequest().body("isbn already exists");
        }

        return  ResponseEntity.ok(bookService.create(book));
    }

    @PutMapping("books/{id}")
    public ResponseEntity<?> update(@Valid @ModelAttribute BookDTO bookDTO,
                                    BindingResult bindingResult,
                                    @PathVariable Long id) {

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var book = bookDTO.getBook();
        book.setId(id);

        if(!bookService.isUniqueIsbn(book.getIsbn(), id)){
            return ResponseEntity.badRequest().body("isbn already exists");
        }

        return bookService.update(book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> runTimeExceptionHandler(RuntimeException exception){
        return ResponseEntity.status(500).body(exception.getMessage());
    }



}
