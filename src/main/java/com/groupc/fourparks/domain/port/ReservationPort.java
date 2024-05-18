package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.Reservation;

import java.util.List;

public interface ReservationPort {
    Reservation save(Reservation reservation);
    List<Reservation> findByUserId(Long userId);
    Reservation findById(Long id);
    void deleteActiveReservation(Long id);
    List<Reservation> findAllReservations();
}
