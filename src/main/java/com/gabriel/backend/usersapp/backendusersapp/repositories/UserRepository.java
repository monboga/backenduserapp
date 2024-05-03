package com.gabriel.backend.usersapp.backendusersapp.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gabriel.backend.usersapp.backendusersapp.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username=?1")
    Optional<User> getUserByUsername(String username);

    // paginador de data.domain
    // recibe un Pageable
    // devueelve el objeto paginador Page
    // metodo optimizado, personalizado para paginacion
    // en el repositorio esta bien que sea el User

    Page<User> findAll(Pageable pageable);

}
