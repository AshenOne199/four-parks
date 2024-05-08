package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.request.UserLoginRequest;
import com.groupc.fourparks.infraestructure.model.request.UserToShow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToShowMapper {

    User toDomain(UserToShow userToShow);
    UserToShow toShow (User user);

}
