package com.groupc.fourparks.infraestructure.rest.controller;

import com.groupc.fourparks.application.usecase.UsersManageService;
import com.groupc.fourparks.domain.model.User;


import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController("/api/v1/users/Users")
public class UsersController
{

    UsersManageService usersManageService;
    //Colocar Endpoint para traer Operaciones de usuarios
    @GetMapping("/api/v1/users/allUsers")
    public ResponseEntity<List<User>> readUsers()
    {
        return new ResponseEntity<List<User>>(this.usersManageService.readUsers() , HttpStatus.OK);
    }
    @GetMapping("/api/v1/users/userByRole{role}")
    public ResponseEntity<List<User>> userByRole(String role)
    {

        return new ResponseEntity<List<User>>(this.usersManageService.userByRole(role) , HttpStatus.OK);

    }
    @GetMapping("/api/v1/users/getOneUser{email}")
    public ResponseEntity<User> getOneUser(String email)
    {

        return new ResponseEntity<>(this.usersManageService.getOneUser(email),HttpStatus.OK);
    }
    @GetMapping("/api/v1/users/deleteUser{email}")
    public  void  deleteUser(String email)
    {
        usersManageService.deleteUser(email);

    }
    @PostMapping("/api/v1/users/modifyUser")
    public ResponseEntity<User> modifyUser(@RequestBody @Valid User user)
    {
        return new ResponseEntity<User>(this.usersManageService.modifyUser(user), HttpStatus.OK);
    }

    @PostMapping("/api/v1/users/createUser")
    public ResponseEntity<User> createUser( @RequestBody @Valid User user)
    {
        return new ResponseEntity<User>(this.usersManageService.createUser(user), HttpStatus.OK);
    }




}
