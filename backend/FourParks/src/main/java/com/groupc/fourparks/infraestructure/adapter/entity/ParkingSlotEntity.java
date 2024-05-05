package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking_slot")
public class ParkingSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ParkingEntity.class)
    @JoinColumn(name = "parking_id")
    private ParkingEntity parkingId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slot_status_id")
    private SlotStatusEntity slotStatusEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_type_id")
    private VehicleTypeEntity vehicleTypeEntity;
}
