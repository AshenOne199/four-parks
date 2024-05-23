package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationPort {
    Reservation save(Reservation reservation);
    List<Reservation> findByUserId(Long userId);
    Reservation findById(Long id);
    void deleteActiveReservation(Long id);
    List<Reservation> findAllReservations();
    List<Reservation> findAllActiveReservationsByParkingId(Long id);
    List<Reservation> findAllActiveReservationsByUserId(Long id);
    List<Reservation> findAllFinishReservationsByUserId(Long id);
    List<Reservation> findAllFinishReservationsByUserIdOptional(Long id);
}
