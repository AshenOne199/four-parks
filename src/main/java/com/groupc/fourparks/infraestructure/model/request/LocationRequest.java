package com.groupc.fourparks.infraestructure.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationRequest {
    Long id;

    @NotNull
    String address;

    @NotNull
    Double latitude;

    @NotNull
    Double longitude;

    @NotNull
    CityRequest city;
}
