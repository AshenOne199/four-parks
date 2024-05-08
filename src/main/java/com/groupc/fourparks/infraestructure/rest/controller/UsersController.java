package com.groupc.fourparks.infraestructure.rest.controller;

import com.groupc.fourparks.application.usecase.UsersManageService;
import com.groupc.fourparks.domain.model.User;

import com.groupc.fourparks.infraestructure.model.request.UserToShow;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UsersController {
    private final UsersManageService usersManageService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserToShow>> readUsers() {
        return new ResponseEntity<>(this.usersManageService.readUsers(), HttpStatus.OK);
    }

    @GetMapping("/userByRole/{role}")
    public ResponseEntity<List<UserToShow>> userByRole( @PathVariable Long role) {
        return new ResponseEntity<>(this.usersManageService.userByRole(role), HttpStatus.OK);
    }

    @GetMapping("/getOneUser/{email}")
    public ResponseEntity<UserToShow> getOneUser(@PathVariable String email) {

        return new ResponseEntity<>(this.usersManageService.getOneUser(email),HttpStatus.OK);
    }

    @GetMapping("/deleteUser/{email}")
    public  void  deleteUser( @PathVariable String email) {
        usersManageService.deleteUser(email);
    }

    @PostMapping("/modifyUser")
    public ResponseEntity<UserToShow> modifyUser(@RequestBody @Valid User user) {
        return new ResponseEntity<>(this.usersManageService.modifyUser(user), HttpStatus.OK);
    }
}
