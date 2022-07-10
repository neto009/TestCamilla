package com.camilla.controller;

import com.camilla.domain.DateConsult;
import com.camilla.domain.Form;
import com.camilla.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/form")
public class FormController {

    @Autowired
    private FormService service;

    @CrossOrigin(origins = "https://camillarocha.herokuapp.com")
    @PostMapping("/cadastro")
    public ResponseEntity<Form> createForm(@RequestBody @Valid Form form) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(form));
    }

    @CrossOrigin(origins = "https://camillarocha.herokuapp.com")
    @PostMapping("/buscaPeriodo")
    public ResponseEntity<List<Form>> findByDate(@RequestBody DateConsult data) {
        return ResponseEntity.status(HttpStatus.OK).body(service.listBetween(data));
    }

    @CrossOrigin(origins = "https://camillarocha.herokuapp.com")
    @GetMapping
    public ResponseEntity<List<Form>>findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
    }
}
