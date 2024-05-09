package com.groupc.fourparks.application.mapper;

import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.infraestructure.model.request.SetAdminToParkingRequest;

@Mapper(componentModel = "spring")
public interface SetAdminToParkingRequestMapper {
    Parking toDomain(SetAdminToParkingRequest SetAdminToParkingRequest);
}
