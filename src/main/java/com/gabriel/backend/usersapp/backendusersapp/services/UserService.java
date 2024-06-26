package com.gabriel.backend.usersapp.backendusersapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gabriel.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.gabriel.backend.usersapp.backendusersapp.models.entities.User;
import com.gabriel.backend.usersapp.backendusersapp.models.request.UserRequest;

public interface UserService {

    List<UserDto> findAll();

    // mismo metodo, pero con una sobrecarga
    // aca interactuamos con lo que devolvemmos al cliente,
    // no el entity completo, sino el Dto.
    Page<UserDto> findAll(Pageable pageable);

    Optional<UserDto> findById(Long id);

    UserDto save(User user);

    Optional<UserDto> update(UserRequest user, Long id);

    void remove(Long id);

}
