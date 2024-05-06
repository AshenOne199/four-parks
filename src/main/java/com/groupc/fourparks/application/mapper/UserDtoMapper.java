package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toDto(User user);

}
