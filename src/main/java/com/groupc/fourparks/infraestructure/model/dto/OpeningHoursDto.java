package com.groupc.fourparks.infraestructure.model.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpeningHoursDto {
    Long id;
    LocalTime openTime;
    LocalTime closeTime;
}
