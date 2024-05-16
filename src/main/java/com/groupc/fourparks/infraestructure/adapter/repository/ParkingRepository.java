package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingEntity, Long>{
    Optional<ParkingEntity> findParkingEntityByName(String name);
    Optional<ParkingEntity> findParkingEntityByAdmin_Id(Long id);
}
