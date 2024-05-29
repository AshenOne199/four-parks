package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.Activity;
import com.groupc.fourparks.infraestructure.model.dto.ActivityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityDtoMapper {
    ActivityDto toDto(Activity auditory);
}


