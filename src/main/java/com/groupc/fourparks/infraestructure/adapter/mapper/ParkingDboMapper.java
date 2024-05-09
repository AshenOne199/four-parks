package com.groupc.fourparks.infraestructure.adapter.mapper;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingDboMapper {
    ParkingEntity toDbo(Parking parking);

    @InheritInverseConfiguration
    Parking toDomain(ParkingEntity parkingEntity);
}
