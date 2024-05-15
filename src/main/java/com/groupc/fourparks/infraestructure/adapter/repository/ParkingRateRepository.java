package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingRateEntity;

@Repository
public interface ParkingRateRepository extends JpaRepository<ParkingRateEntity, Long>{
    List<ParkingRateEntity> findByParkingId(ParkingEntity parkingId);
}
