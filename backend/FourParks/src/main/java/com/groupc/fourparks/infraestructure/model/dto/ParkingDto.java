package com.groupc.fourparks.infraestructure.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long id;
    String available_slots;
    String loyalty;
    String name;
    String total_slots;
    LocationDto location;
}
