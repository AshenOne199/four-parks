package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.infraestructure.model.dto.LocationDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationDtoMapper {
    LocationDto toDto(Location location);

    @InheritInverseConfiguration
    Location toDomain(LocationDto locationDtoMapper);
}
