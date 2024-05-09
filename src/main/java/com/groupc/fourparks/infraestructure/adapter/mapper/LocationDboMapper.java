package com.groupc.fourparks.infraestructure.adapter.mapper;

import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.infraestructure.adapter.entity.LocationEntity;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationDboMapper{
    
    LocationEntity toDbo(Location location);

    @InheritInverseConfiguration
    Location toDomain(LocationEntity locationEntity);
}
