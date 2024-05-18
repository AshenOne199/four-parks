package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.Reservation;
import com.groupc.fourparks.infraestructure.model.dto.ReservationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationRequestMapper {
    ReservationDto toDto(Reservation reservation);
    Reservation toDomain(ReservationDto reservationDto);
}
