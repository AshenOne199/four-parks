package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;
import com.groupc.fourparks.infraestructure.model.request.UserToShow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


public interface UsersManageService
{
     List<UserToShow> readUsers();
     List<UserToShow> userByRole(Long role);
     UserToShow modifyUser(User User);
     UserToShow getOneUser(String email);
     void deleteUser(String userEmail);


   /* User createUser(User user);*/
   /* User createUser(User user);*/
}