package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.VehicleTypeEntity;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Long>{
    Optional<VehicleTypeEntity> findVehicleTypeByType(String type);
}
