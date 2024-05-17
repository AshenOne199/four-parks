package com.groupc.fourparks.infraestructure.adapter.repository;

import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepositoryCustom extends JpaRepository<ParkingSlotDetailsDto, Long> {
    @Query(value = "SELECT new ParkingSlotDetailsDto(COUNT(vt.type), vt.type) FROM ParkingEntity p JOIN ParkingSlotEntity ps ON p.id = ps.parkingId.id JOIN VehicleTypeEntity vt ON ps.vehicleTypeId.id = vt.id JOIN SlotStatusEntity s ON ps.slotStatusId.id = s.id WHERE p.id = ?1 AND s.status = 'EMPTY' GROUP BY vt.type")
    List<ParkingSlotDetailsDto> findEmptySlotsByParkingId(Long parkingId);
}
