package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking_rate")
public class ParkingRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate")
    private float rate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehicle_type_id")
    private VehicleTypeEntity vehicleTypeId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parking_id")
    private ParkingEntity parkingId;
}
