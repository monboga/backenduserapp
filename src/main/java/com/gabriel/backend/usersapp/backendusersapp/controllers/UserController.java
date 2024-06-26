package com.gabriel.backend.usersapp.backendusersapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.gabriel.backend.usersapp.backendusersapp.models.entities.User;
import com.gabriel.backend.usersapp.backendusersapp.models.request.UserRequest;
import com.gabriel.backend.usersapp.backendusersapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDto> list() {
        return service.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<UserDto> list(@PathVariable Integer page) {

        // los parametros es la pagina inicial, mas el tamaño que mostrara en el
        // listado, que en este caso es 6

        Pageable pageable = PageRequest.of(page, 6);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<UserDto> userOptional = service.findById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }

        // retorna un error 404 donde no se encontro lo buscado
        return ResponseEntity.notFound().build();
    }

    // endpoint sin ninguna ruta
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        // retorna el usuario de la base de datos creado
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<UserDto> o = service.update(user, id);

        // validar
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {

        Optional<UserDto> o = service.findById(id);

        if (o.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); // 204 no devuelve nada, solo elimina

        }

        return ResponseEntity.notFound().build();

    }

    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);

    }
}
