package com.groupc.fourparks.infraestructure.model.request;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OpeningHoursRequest {
    Long id;
    LocalTime close_time;
    LocalTime open_time;
}
