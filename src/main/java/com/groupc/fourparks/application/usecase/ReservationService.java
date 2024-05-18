package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.infraestructure.model.dto.ReservationDto;
import com.groupc.fourparks.infraestructure.model.request.ReservationRequest;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(ReservationRequest reservationRequest);
    void deleteReservation(Long id);
    ReservationDto startReservation(ReservationRequest reservationRequest);
    ReservationDto endReservation(ReservationRequest reservationEndRequest);
    List<ReservationDto> getAllReservations();
}
