package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingSlotEntity;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlotEntity, Long>{
    List<ParkingSlotEntity> findByParkingId(ParkingEntity parkingId);
}
