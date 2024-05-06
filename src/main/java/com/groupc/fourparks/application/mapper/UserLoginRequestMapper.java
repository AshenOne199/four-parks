package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.request.UserLoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserLoginRequestMapper {

    User toDomain(UserLoginRequest userLoginRequest);

}
