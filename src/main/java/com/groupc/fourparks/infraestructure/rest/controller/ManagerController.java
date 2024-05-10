package com.groupc.fourparks.infraestructure.rest.controller;

import com.groupc.fourparks.application.usecase.ManagerService;
import com.groupc.fourparks.domain.model.User;

import com.groupc.fourparks.infraestructure.model.request.UserToShow;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://fourparks.vercel.app/")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/all")
    public ResponseEntity<List<UserToShow>> getAllUsers() {
        return new ResponseEntity<>(this.managerService.readUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/role/{role}")
    public ResponseEntity<List<UserToShow>> getUserByRole(@PathVariable Long role) {
        return new ResponseEntity<>(this.managerService.userByRole(role), HttpStatus.OK);
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserToShow> getUser(@PathVariable String email) {
        return new ResponseEntity<>(this.managerService.getOneUser(email),HttpStatus.OK);
    }

    @GetMapping("/user/delete/{email}")
    public void deleteUser( @PathVariable String email) {
        managerService.deleteUser(email);
    }

    @PostMapping("/user/modify")
    public ResponseEntity<UserToShow> modifyUser(@RequestBody @Valid User user) {
        return new ResponseEntity<>(this.managerService.modifyUser(user), HttpStatus.OK);
    }
}
