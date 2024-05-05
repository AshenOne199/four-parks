package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewParkingRequestMapper {
    NewParkingRequest toDto(Parking parking);

    @InheritInverseConfiguration
    Parking toDomain(NewParkingRequest newParkingRequest);
}
