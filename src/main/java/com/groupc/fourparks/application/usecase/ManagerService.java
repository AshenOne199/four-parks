package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;
import com.groupc.fourparks.infraestructure.model.request.UserToShow;

import java.util.List;

public interface ManagerService {
     List<UserDto> readUsers();
     List<UserDto> userByRole(Long role);
     UserDto modifyUser(UserRegisterRequest User);
     UserDto getOneUser(String email);
     void deleteUser(String userEmail);

     List<UserDto> getFreeAdmins();
}