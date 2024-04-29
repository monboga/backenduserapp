package com.gabriel.backend.usersapp.backendusersapp.models.dto.mapper;

import com.gabriel.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.gabriel.backend.usersapp.backendusersapp.models.entities.User;

// creamos la clase comun y corriente
public class DtoMapperUser {

    // no utilizar una variable estatica.
    // private static DtoMapperUser mapper;

    private User user;



    // creamos un constructor privado
    private DtoMapperUser() {

    }

    // despues un constructor estatico
    public static DtoMapperUser builder() {
        return new DtoMapperUser();
    }

    // se retorna la misma instancia
    public DtoMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDto build() {
        if(user == null) {
            throw new RuntimeException("Debe pasar el entity user!");
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
        return new UserDto(this.user.getId(), user.getUsername(), user.getEmail(), isAdmin);

    }

    

}
