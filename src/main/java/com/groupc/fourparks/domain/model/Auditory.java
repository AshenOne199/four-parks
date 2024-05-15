package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.ActivityEntity;
import lombok.*;

import java.util.Date;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auditory {
    private Long id;
    private String ip;
    private Date happening_date;
    private ActivityEntity activity;
}
