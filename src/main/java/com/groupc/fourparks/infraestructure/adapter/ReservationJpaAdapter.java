package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Reservation;
import com.groupc.fourparks.domain.port.ReservationPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.ReservationDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ReservationRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
