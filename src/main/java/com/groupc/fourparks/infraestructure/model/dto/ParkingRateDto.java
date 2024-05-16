package com.groupc.fourparks.infraestructure.model.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ParkingRateDto {
    Long id;
    Double rate;
    @JsonIgnore
    ParkingDto parkingId;
    VehicleTypeDto vehicleTypeId;
}
