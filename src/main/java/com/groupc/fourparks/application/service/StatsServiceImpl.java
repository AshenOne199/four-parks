package com.groupc.fourparks.application.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.application.usecase.ReservationService;
import com.groupc.fourparks.application.usecase.StatsService;
import com.groupc.fourparks.infraestructure.model.dto.ReservationDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final ReservationService reservationService;

    @Override
    public String incomesOnDate(Date beginning, Date ending, Long id) {
        List<ReservationDto> reservationsReceiver = new ArrayList<>();
        reservationsReceiver = reservationService.getAllReservations();
        double totalAmount = 0;
        for (ReservationDto reservationDto : reservationsReceiver) {
            LocalDateTime ldt = reservationDto.getReservationStartTime();
            ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
            Date comparableDate = Date.from(zdt.toInstant());

            if (id == -1) {
                if ((comparableDate.after(beginning) && comparableDate.before(ending))) {
                    totalAmount += reservationDto.getTotalPrice();
                }

            } else {
                if ((comparableDate.after(beginning) && reservationDto.getParkingSlot().getParkingId().getId() == id
                        && comparableDate.before(ending))) {
                    totalAmount += reservationDto.getTotalPrice();
                }
            }

        }
        String returnable = "" + totalAmount;

        return returnable;
    }

    @Override
    public List<UserDto> getUsersForParking(Date beginning, Date ending, Long id) {
        List<ReservationDto> reservationsReceiver = reservationService.getAllReservations();
        List<UserDto> returnable = new ArrayList<>();
        for (ReservationDto reservationDto : reservationsReceiver) {
            System.out.println(reservationDto.getParkingSlot().getParkingId().getId());

            LocalDateTime ldt = reservationDto.getReservationStartTime();

            ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
            Date comparableDate = Date.from(zdt.toInstant());
            UserDto addable = reservationDto.getUser();
            if (id == -1) {
                if (comparableDate.after(beginning) && comparableDate.before(ending)) {
                    boolean addableConfirmation = true;
                    for (UserDto userDto : returnable) {
                        if (userDto.getId() == addable.getId())
                            addableConfirmation = false;
                    }
                    if (addableConfirmation) {

                        returnable.add(addable);
                    }

                }

            } else {
                if ((comparableDate.after(beginning) && comparableDate.before(ending)
                        && !returnable.contains(reservationDto.getUser())
                        && reservationDto.getParkingSlot().getParkingId().getId() == id)) {
                            boolean addableConfirmation = true;
                            for (UserDto userDto : returnable) {
                                if (userDto.getId() == addable.getId())
                                    addableConfirmation = false;
                            }
                            if (addableConfirmation) {
        
                                returnable.add(addable);
                            }
                }
            }
        }

        return returnable;
    }

}
