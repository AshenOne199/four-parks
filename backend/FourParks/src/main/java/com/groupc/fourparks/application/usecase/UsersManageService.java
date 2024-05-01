package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


public interface UsersManageService
{
     List<User> readUsers();
     List<User> userByRole(Long role);
     User modifyUser(User user);
     User getOneUser(String email);
     void deleteUser(String userEmail);


   /* User createUser(User user);*/
}