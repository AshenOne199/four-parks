package com.groupc.fourparks.domain.model;

import java.time.LocalTime;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeningHours {
    private Long id;
    private LocalTime openTime;
    private LocalTime closeTime;
}
