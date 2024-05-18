package com.groupc.fourparks.infraestructure.adapter.mapper;

import com.groupc.fourparks.domain.model.Reservation;
import com.groupc.fourparks.infraestructure.adapter.entity.ReservationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationDboMapper {

    ReservationEntity toDbo(Reservation domain);

    @InheritInverseConfiguration
    Reservation toDomain(ReservationEntity reservationEntity);
}
