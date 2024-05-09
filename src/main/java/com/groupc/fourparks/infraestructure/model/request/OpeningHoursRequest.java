package com.groupc.fourparks.infraestructure.model.request;

import java.text.ParseException;
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
    public OpeningHoursRequest(String close_time, String open_time) throws ParseException {
        this.close_time = LocalTime.parse(close_time);
        this.open_time = LocalTime.parse(open_time);
    }
    
}
