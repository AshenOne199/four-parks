package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.dto.LoginDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginDtoMapper {

    LoginDto toDto(User user);

}
