package com.gabriel.backend.usersapp.backendusersapp.models.request;

import com.gabriel.backend.usersapp.backendusersapp.models.IUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest implements IUser{

    // se puebla el user que viene del cuerpo del request.
    @NotBlank
    @Size(min = 4, max = 8)
    private String username;

    @NotBlank
    @Email
    private String email;

    private boolean admin;


    @Override
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    

}
