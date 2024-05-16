package com.groupc.fourparks.application.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.City;
import com.groupc.fourparks.infraestructure.model.dto.CityDto;

@Mapper(componentModel = "spring")
public interface CityDtoMapper {
     CityDto toDto(City city);

    @InheritInverseConfiguration
    City toDomain(CityDto cityDto);
}
