package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;

import java.util.List;
import java.util.Set;

public interface UsersManageService
{
    public List<User> readUsers(/*Cargo*/);
    public List<User> userByRole(String roles);
    public User modifyUser(User user);
    public User getOneUser(String email);
    public void deleteUser(String userEmail);


    User createUser(User user);
}