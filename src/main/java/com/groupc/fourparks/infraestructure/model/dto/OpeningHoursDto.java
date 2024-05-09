package com.groupc.fourparks.infraestructure.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OpeningHoursDto {
    Long id;
    String open_time;
    String close_time;
}
