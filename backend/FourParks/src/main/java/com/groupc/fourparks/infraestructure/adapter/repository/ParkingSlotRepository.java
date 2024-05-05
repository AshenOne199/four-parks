package com.groupc.fourparks.infraestructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingSlotEntity;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlotEntity, Long>{
    
}
