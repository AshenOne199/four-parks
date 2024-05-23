package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Reservation;
import com.groupc.fourparks.domain.port.ReservationPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.ReservationDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ReservationRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationJpaAdapter implements ReservationPort {

    private final ReservationRepository reservationRepository;
    private final ReservationDboMapper reservationDboMapper;

    @Override
    public Reservation save(Reservation reservation) {
        var reservationToSave = reservationDboMapper.toDbo(reservation);
        var reservationSaved = reservationRepository.save(reservationToSave);
        return reservationDboMapper.toDomain(reservationSaved);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        var listReservation = reservationRepository.findByUserId(userId);
        return listReservation.stream()
                .map(reservationDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Reservation findById(Long id) {
        var reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new NotFoundException("La reserva con id: " + id + " no existe");
        }
        return reservationDboMapper.toDomain(reservation.get());
    }

    public void deleteActiveReservation(Long id) {
        var reservation = reservationRepository.findById(id);
        reservation.ifPresent(reservationRepository::delete);
    }

    @Override
    public List<Reservation> findAllReservations() {
        var listReservation = reservationRepository.findAll();
        return listReservation.stream()
                .map(reservationDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findAllActiveReservationsByParkingId(Long parkingId) {
        var activeReservationList = reservationRepository.findActiveReservationsByParkingId(parkingId);
        return activeReservationList.stream()
                .map(reservationDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findAllActiveReservationsByUserId(Long id) {
        var activeReservationList = reservationRepository.findActiveReservationsByUserId(id);
        if (activeReservationList.isEmpty()) {
            throw new NotFoundException("El usuario con: " + id + " no tiene reservas activas");
        }
        return activeReservationList.stream()
                .map(reservationDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findAllFinishReservationsByUserId(Long id) {
        var finishReservationsByUserId = reservationRepository.findFinishReservationsByUserId(id);
        if (finishReservationsByUserId.isEmpty()) {
            throw new NotFoundException("El usuario con: " + id + " no tiene reservas completadas");
        }
        return finishReservationsByUserId.stream()
                .map(reservationDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findAllFinishReservationsByUserIdOptional(Long id) {
        var finishReservationsByUserId = reservationRepository.findFinishReservationsByUserId(id);
        if (finishReservationsByUserId.isEmpty()) {
            return null;
        }
        return finishReservationsByUserId.stream()
                .map(reservationDboMapper::toDomain)
                .collect(Collectors.toList());
    }
}
