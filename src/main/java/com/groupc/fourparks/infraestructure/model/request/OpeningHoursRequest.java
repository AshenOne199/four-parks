package com.groupc.fourparks.infraestructure.model.request;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OpeningHoursRequest {
    Long id;
    @NotNull
    LocalTime closeTime;

    @NotNull
    LocalTime openTime;
}
