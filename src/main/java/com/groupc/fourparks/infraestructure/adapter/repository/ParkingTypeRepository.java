package com.groupc.fourparks.infraestructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.ParkingTypeEntity;

import java.util.Optional;

@Repository
public interface ParkingTypeRepository extends JpaRepository<ParkingTypeEntity, Long>{
    Optional<ParkingTypeEntity> findParkingTypeByType(String type);
}
