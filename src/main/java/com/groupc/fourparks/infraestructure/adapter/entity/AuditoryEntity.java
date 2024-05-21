package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auditory")

public class AuditoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "happening_date")
    private Date happeningDate;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private ActivityEntity activity;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private UserEntity user;
}






