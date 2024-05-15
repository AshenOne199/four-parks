package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;
import java.util.List;

public interface ManagerService {
     List<UserDto> readUsers();
     List<UserDto> userByRole(Long role);
     UserDto modifyUser(UserRegisterRequest User);
     UserDto getOneUser(String email);
     void deleteUser(String userEmail);
}