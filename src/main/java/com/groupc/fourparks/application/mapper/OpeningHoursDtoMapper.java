package com.groupc.fourparks.application.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.infraestructure.model.dto.OpeningHoursDto;

@Mapper(componentModel = "spring")
public interface OpeningHoursDtoMapper {
    OpeningHoursDto toDto(OpeningHours openingHours);

    @InheritInverseConfiguration
    OpeningHours toDomain(OpeningHoursDto openingHoursDto);
}
