package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location",uniqueConstraints = { @UniqueConstraint(columnNames = { "address"})})
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(targetEntity = CityEntity.class,cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private CityEntity city;
}
