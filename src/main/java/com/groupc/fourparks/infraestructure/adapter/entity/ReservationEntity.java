package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    @Column(name = "reservation_start_time")
    private LocalDateTime reservationStartTime;

    @Column(name = "reservation_end_time")
    private LocalDateTime reservationEndTime;

    @Column(name = "total_price")
    private Float totalPrice;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(targetEntity = ParkingSlotEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "parking_slot_id")
    private ParkingSlotEntity parkingSlot;

    @OneToOne(targetEntity = VehicleTypeEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehicle_type_id")
    private VehicleTypeEntity vehicleType;

}
