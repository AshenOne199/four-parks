package com.groupc.fourparks.infraestructure.adapter.entity;

/*import java.util.HashSet;
import java.util.Set;*/

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
    private int total_slots;

    @Column(name = "available_slots")
    private int available_slots;

    @Column(name = "loyalty")
    private boolean loyalty;

    @Column(name = "admin_id")
    private UserEntity adminId;

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
