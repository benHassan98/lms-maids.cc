package com.library.librarymanagementsystem.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.librarymanagementsystem.dto.PatronDTO;
import com.library.librarymanagementsystem.service.PatronServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PatronController {

    private final PatronServiceImpl patronService;

    @Autowired
    public PatronController(PatronServiceImpl patronService) {
        this.patronService = patronService;
    }
    
    @GetMapping("patrons")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(patronService.findAll());
    }

    @GetMapping("patrons/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return patronService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("patrons")
    public ResponseEntity<?> create(@Valid @ModelAttribute PatronDTO patronDTO,
                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return  ResponseEntity.ok(patronService.create(patronDTO.getPatron()));
    }

    @PutMapping("patrons/{id}")
    public ResponseEntity<?> update(@Valid @ModelAttribute PatronDTO patronDTO,
                                    BindingResult bindingResult,
                                    @PathVariable Long id) {

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var patron = patronDTO.getPatron();
        patron.setId(id);

        return patronService.update(patron)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("patrons/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        patronService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> runTimeExceptionHandler(RuntimeException exception){
        return ResponseEntity.status(500).body(exception.getMessage());
    }





}
