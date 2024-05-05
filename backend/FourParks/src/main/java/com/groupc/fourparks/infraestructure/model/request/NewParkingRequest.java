package com.groupc.fourparks.infraestructure.model.request;
import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.domain.model.ParkingType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewParkingRequest {
    @NotBlank
    String available_slots;

    @NotBlank
    String loyalty;

    @NotBlank
    String name;

    @NotBlank
    String total_slots;

    LocationRequest location;

    OpeningHoursRequest openingHours;

    ParkingTypeRequest parkingType;

}
