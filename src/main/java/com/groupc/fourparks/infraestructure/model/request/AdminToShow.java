package com.groupc.fourparks.infraestructure.model.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminToShow {
    Long id;
    String email;
    String firstName;
    String secondName;
    String firstLastname;
    String secondLastname;
}
