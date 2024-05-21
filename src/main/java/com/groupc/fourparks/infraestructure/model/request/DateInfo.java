package com.groupc.fourparks.infraestructure.model.request;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DateInfo {
    LocalDate beginning;
    LocalDate ending;

}
