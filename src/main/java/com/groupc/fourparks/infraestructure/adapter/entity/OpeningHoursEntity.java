package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "opening_hours")
public class OpeningHoursEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "open_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @Column(name = "close_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;
}
