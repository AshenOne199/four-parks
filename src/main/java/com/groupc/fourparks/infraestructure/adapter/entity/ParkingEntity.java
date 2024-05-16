package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking")
public class ParkingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "total_slots")
    private Integer totalSlots;

    @Column(name = "car_slots")
    private Integer carSlots;

    @Column(name = "bicycle_slots")
    private Integer bicycleSlots;

    @Column(name = "motorcycle_slots")
    private Integer motorcycleSlots;

    @Column(name = "heavy_vehicle_slots")
    private Integer heavyVehicleSlots;
    
    @Column(name = "available_slots")
    private Integer availableSlots;

    @Column(name = "loyalty")
    private Boolean loyalty;

    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "admin_id")
    private UserEntity admin;

    @OneToOne(targetEntity = LocationEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToOne(targetEntity = ParkingTypeEntity.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "parking_type_id")
    private ParkingTypeEntity parkingType;

    @ManyToOne(targetEntity = OpeningHoursEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "opening_hours_id")
    private OpeningHoursEntity openingHours;

}
