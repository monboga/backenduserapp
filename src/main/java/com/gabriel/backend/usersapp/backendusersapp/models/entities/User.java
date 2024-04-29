package com.gabriel.backend.usersapp.backendusersapp.models.entities;

import java.util.List;

import com.gabriel.backend.usersapp.backendusersapp.models.IUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User implements IUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Max & Min se utilizan cuando son numeros long, big decimal, etc.
    @NotBlank
    @Size(min = 4, max = 8)
    // si no especificamos el nombre de la tabla, es el mismo de la variable
    @Column(unique = true)
    private String username;

    // validacion de campos vacios en el backend
    @NotBlank
    private String password;

    @NotBlank
    @Email
    // especificamos que el tipo de dato es unico
    @Column(unique = true)
    private String email;

    // un usuario puede tener muchos roles y puede tener muchos usuarios
    // debemos tener una tabla intermedia llamada "tabla de enlaces" con el id rol y id user
    @ManyToMany
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id"),
        uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"})}) //el conjunto de id no se puede repetir
    private List<Role> roles;

    // es un campo solamente de la clase, pero no se mapea a la base de datos.
    @Transient
    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    
    
}
