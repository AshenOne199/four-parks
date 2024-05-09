package com.groupc.fourparks.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "slot_status",uniqueConstraints = { @UniqueConstraint(columnNames = {"status"})})
public class SlotStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;
}
