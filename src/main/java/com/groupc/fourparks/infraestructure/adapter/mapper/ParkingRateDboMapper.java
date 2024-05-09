package com.groupc.fourparks.infraestructure.adapter.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingRateEntity;

@Mapper(componentModel = "spring")
public interface ParkingRateDboMapper {
    ParkingRateEntity toDbo(ParkingRate parkingRate);

    @InheritInverseConfiguration
    ParkingRate toDomain(ParkingRateEntity parkingRateEntity);
}
