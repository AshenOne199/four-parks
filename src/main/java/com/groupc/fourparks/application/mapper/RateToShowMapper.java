package com.groupc.fourparks.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.infraestructure.model.request.RateToShow;

@Mapper(componentModel = "spring")
public interface RateToShowMapper {
    @Mappings({
      @Mapping(target="id", source="id"),
      @Mapping(target="rate", source="rate"),
      @Mapping(target="parkingId", source="parkingRate.parkingId.id"),
      @Mapping(target="vehicleTypeId", source="parkingRate.vehicleTypeId.type")
    })
    RateToShow toShow(ParkingRate parkingRate);
}
