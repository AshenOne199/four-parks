package com.groupc.fourparks.infraestructure.adapter.mapper;

import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.infraestructure.adapter.entity.OpeningHoursEntity;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpeningHoursDboMapper {
    OpeningHoursEntity toDbo(OpeningHours openingHours);

    @InheritInverseConfiguration
    OpeningHours toDomain(OpeningHoursEntity openingHoursEntity);
}
