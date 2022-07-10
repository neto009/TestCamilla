package com.camilla.controller;

import com.camilla.domain.Form;
import com.camilla.domain.Usuario;
import com.camilla.excel.FormExcelExporter;
import com.camilla.service.FormService;
import com.camilla.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private FormService service;

    @Autowired
    private UsuarioService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> participante(@PathVariable Long id) {
        Usuario existingItemOptional = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(existingItemOptional);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> users() {
        try {
            List<Usuario> items = userService.todos();
            if (items.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            return ResponseEntity.status(HttpStatus.OK).body(items);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> novo(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualiza(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuario1 = userService.update(id, usuario);

        if (usuario1 != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, usuario));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Form> listForm = service.listAll();
        FormExcelExporter excelExporter = new FormExcelExporter(listForm);
        excelExporter.export(response);
    }
}
