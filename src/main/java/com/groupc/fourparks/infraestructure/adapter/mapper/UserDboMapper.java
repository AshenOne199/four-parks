package com.groupc.fourparks.infraestructure.adapter.mapper;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDboMapper {

    UserEntity toDbo(User domain);

    @InheritInverseConfiguration
    User toDomain(UserEntity userEntity);

}
