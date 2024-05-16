package com.groupc.fourparks.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.infraestructure.model.request.ParkingToShow;

@Mapper(componentModel = "spring")
public interface ParkingToShowMapper {
    @Mappings({
      @Mapping(target="parkingType", source="parking.parkingType.type"),
      @Mapping(target="longitude", source="parking.location.longitude"),
      @Mapping(target="latitude", source="parking.location.latitude"),
      @Mapping(target = "address", source = "parking.location.address"),
      @Mapping(target = "city", source = "parking.location.city.city"),
      @Mapping(target = "close_time", source = "parking.openingHours.close_time"),
      @Mapping(target = "open_time", source = "parking.openingHours.open_time"),
      @Mapping(target = "adminToShow", source = "parking.admin")
    })
    ParkingToShow toShow(Parking parking);
}
