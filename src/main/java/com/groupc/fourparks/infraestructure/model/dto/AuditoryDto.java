package com.groupc.fourparks.infraestructure.model.dto;

import com.groupc.fourparks.domain.model.Activity;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditoryDto {

    private Long id;
    private String ip;
    private Date happening_date;
    private Activity activity;
    private UserDto UserDto;
}






