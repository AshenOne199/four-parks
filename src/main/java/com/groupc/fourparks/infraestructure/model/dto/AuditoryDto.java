package com.groupc.fourparks.infraestructure.model.dto;

import com.groupc.fourparks.infraestructure.adapter.entity.ActivityEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditoryDto {

    private String ip;
    private Date happening_date;
    private ActivityEntity activity;
}






