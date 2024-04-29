package com.gabriel.backend.usersapp.backendusersapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.backend.usersapp.backendusersapp.models.IUser;
import com.gabriel.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.gabriel.backend.usersapp.backendusersapp.models.dto.mapper.DtoMapperUser;
import com.gabriel.backend.usersapp.backendusersapp.models.entities.Role;
import com.gabriel.backend.usersapp.backendusersapp.models.entities.User;
import com.gabriel.backend.usersapp.backendusersapp.models.request.UserRequest;
import com.gabriel.backend.usersapp.backendusersapp.repositories.RoleRepository;
import com.gabriel.backend.usersapp.backendusersapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    // ctrl + . sobre el error y agregar los metodos no implementados
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {

        List<User> user =  (List<User>) repository.findAll();

        return user
        .stream()
        .map(u -> DtoMapperUser.builder().setUser(u).build())
        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)

    public Optional<UserDto> findById(Long id) {
       return  repository.findById(id).map(u -> DtoMapperUser
       .builder()
       .setUser(u)
       .build());
    }

    @Override
    @Transactional
    public UserDto save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(getRoles(user));

       return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }

    

    @Override
    @Transactional
    public Optional<UserDto> update(UserRequest user, Long id) {
        
        Optional<User> o = repository.findById(id);
        User userOptional = null;
        // validar
        if(o.isPresent()) {
            User userDb = o.orElseThrow();

            userDb.setRoles(getRoles(user));
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            // guardar el objeto actualizado.
            // pasando el userDb porque es el usuario almacenado en la base de datos.
            userOptional = repository.save(userDb);
            
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(userOptional).build());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    private List<Role> getRoles(IUser user) {
        Optional<Role> ou = roleRepository.findByName("ROLE_USER");


        List<Role> roles = new ArrayList<>();

        if(ou.isPresent()) {
            roles.add(ou.orElseThrow());
        } 

        if(user.isAdmin()) {
            // si es administrador, busca el rol en la base de datos
            Optional<Role> oa = roleRepository.findByName("ROLE_ADMIN");
            if(oa.isPresent()) {
                roles.add(oa.orElseThrow());
            }
        }

        return roles;
    }



}
