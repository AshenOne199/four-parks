package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.request.UserToShow;

import java.util.List;

public interface ManagerService {
     List<UserToShow> readUsers();
     List<UserToShow> userByRole(Long role);
     UserToShow modifyUser(User User);
     UserToShow getOneUser(String email);
     void deleteUser(String userEmail);
}