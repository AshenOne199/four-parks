package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.mapper.ParkingSlotDtoMapper;
import com.groupc.fourparks.application.mapper.ReservationRequestMapper;
import com.groupc.fourparks.application.mapper.SlotStatusDtoMapper;
import com.groupc.fourparks.application.mapper.UserDtoMapper;
import com.groupc.fourparks.application.usecase.AuditoryService;
import com.groupc.fourparks.application.usecase.ReservationService;
import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.domain.port.*;
import com.groupc.fourparks.infraestructure.exception.BadRequestException;
import com.groupc.fourparks.infraestructure.model.dto.ReservationDto;
import com.groupc.fourparks.infraestructure.model.request.ReservationRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final UserPort userPort;
    private final ParkingSlotPort parkingSlotPort;
    private final ReservationPort reservationPort;
    private final SlotStatusPort slotStatusPort;
    private final ParkingRatePort parkingRatePort;
    private final ParkingPort parkingPort;

    private final ReservationRequestMapper reservationRequestMapper;
    private final UserDtoMapper userDtoMapper;
    private final ParkingSlotDtoMapper parkingSlotDtoMapper;
    private final SlotStatusDtoMapper slotStatusDtoMapper;

    private final AuditoryService auditoryService;

    @Override
    @Transactional
    public ReservationDto createReservation(ReservationRequest reservation) {
        var userToSave = userPort.findUserById(reservation.getUserId());

        var usersWithReservation = reservationPort.findByUserId(reservation.getUserId());
        usersWithReservation.stream()
                .filter(reservationItem -> reservationItem.getReservationEndTime() == null)
                .forEach(reservationItem -> {
                    throw new BadRequestException("El usuario ya tiene una reserva activa");
                });

        var parkingSlot = parkingSlotPort.getParkingSlot(reservation.getParkingSlotId());
        if (parkingSlot.getSlotStatusId().getStatus().equals("FULL")) {
            throw new BadRequestException("El cupo ya esta reservado");
        }
        var parkingSlotDto = parkingSlotDtoMapper.toDto(parkingSlot);
        var statusSlot = slotStatusPort.findSlotStatusByStatus("FULL");
        var slotStatusDto = slotStatusDtoMapper.toDto(statusSlot);
        parkingSlotDto.setSlotStatusId(slotStatusDto);
        var parkingSlotToSave = parkingSlotDtoMapper.toDomain(parkingSlotDto);
        parkingSlotPort.save(parkingSlotToSave);

        var reservationDto = ReservationDto.builder()
                .reservationTime(reservation.getReservationTime())
                .user(userDtoMapper.toDto(userToSave))
                .parkingSlot(parkingSlotDtoMapper.toDto(parkingSlotToSave))
                .build();

        var reservationToCreate = reservationRequestMapper.toDomain(reservationDto);
        var reservationSaved = reservationPort.save(reservationToCreate);
        auditoryService.registerAuditory(5L, userToSave.getId());
        return reservationRequestMapper.toDto(reservationSaved);
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        var reservationsByUser = reservationPort.findByUserId(id);
        reservationsByUser.stream()
                .filter(reservationItem -> reservationItem.getReservationEndTime() == null)
                .forEach(reservationItem -> reservationPort.deleteActiveReservation(reservationItem.getId()));
    }

    @Override
    public ReservationDto startReservation(ReservationRequest reservationRequest) {
        var reservation = reservationPort.findById(reservationRequest.getIdReservation());
        LocalDateTime timeNow = LocalDateTime.now();
        reservation.setReservationStartTime(timeNow);
        var reservationSaved = reservationPort.save(reservation);
        return reservationRequestMapper.toDto(reservationSaved);
    }

    @Override
    public ReservationDto endReservation(ReservationRequest reservationRequest) {
        var reservation = reservationPort.findById(reservationRequest.getIdReservation());
        LocalDateTime timeNow = LocalDateTime.now();
        reservation.setReservationEndTime(timeNow);
        if (reservation.getReservationStartTime() == null)  {
            throw new BadRequestException("La reserva aun no se ha iniciado");
        }
        Duration timeDuration = Duration.between(reservation.getReservationStartTime(), timeNow);

        var listParkingRates = parkingRatePort.getParkingRatesByParking(parkingPort.findById(reservation.getParkingSlot().getParkingId().getId()));

        Optional<Double> optionalFinalRate = listParkingRates.stream()
                .filter(rate -> rate.getVehicleTypeId().getType().equals(reservation.getParkingSlot().getVehicleTypeId().getType()))
                .map(ParkingRate::getRate)
                .findFirst();

        if (optionalFinalRate.isEmpty()){
            throw new BadRequestException("Este tipo de vehiculo no tiene un precio asocidao");
        }

        Float total = reservation.calculateTotal(timeDuration.toMinutes(), optionalFinalRate.get());
        if (reservation.getParkingSlot().getParkingId().getLoyalty()){
            var finishReservation = reservationPort.findAllFinishReservationsByUserIdOptional(reservation.getUser().getId());
            if (finishReservation != null && !finishReservation.isEmpty() && finishReservation.size() > 3){
                reservation.setDiscount(true);
                total = total - (total * 30)/100;
            }
        }
        reservation.setTotalPrice(total);

        var parkingSlot = parkingSlotPort.getParkingSlot(reservation.getParkingSlot().getId());
        var parkingSlotDto = parkingSlotDtoMapper.toDto(parkingSlot);
        var statusSlot = slotStatusPort.findSlotStatusByStatus("EMPTY");
        var slotStatusDto = slotStatusDtoMapper.toDto(statusSlot);
        parkingSlotDto.setSlotStatusId(slotStatusDto);
        var parkingSlotToSave = parkingSlotDtoMapper.toDomain(parkingSlotDto);
        parkingSlotPort.save(parkingSlotToSave);

        var reservationSaved = reservationPort.save(reservation);

        return reservationRequestMapper.toDto(reservationSaved);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        var listReservation = reservationPort.findAllReservations();
        return listReservation.stream()
                .map(reservationRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByParkingId(Long parkingId) {
        var parkingFound = parkingPort.findById(parkingId);
        var reservations = reservationPort.findAllActiveReservationsByParkingId(parkingFound.getId());
        return reservations.stream()
                .map(reservationRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        var reservationFound = reservationPort.findById(id);
        return reservationRequestMapper.toDto(reservationFound);
    }

    @Override
    public List<ReservationDto> getReservationsActiveByUserId(Long userId) {
        var userFound = userPort.findUserById(userId);
        var reservations = reservationPort.findAllActiveReservationsByUserId(userFound.getId());
        return reservations.stream()
                .map(reservationRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsFinishByUserId(Long userId) {
        var userFound = userPort.findUserById(userId);
        var reservations = reservationPort.findAllFinishReservationsByUserId(userFound.getId());
        return reservations.stream()
                .map(reservationRequestMapper::toDto)
                .collect(Collectors.toList());
    }
}
