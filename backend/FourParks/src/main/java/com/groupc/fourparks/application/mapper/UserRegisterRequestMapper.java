package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegisterRequestMapper {

    User toDomain(UserRegisterRequest userRegisterRequest);

}
