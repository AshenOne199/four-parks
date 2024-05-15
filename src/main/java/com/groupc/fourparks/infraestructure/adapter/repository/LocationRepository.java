package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.LocationEntity;


@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long>{
        Optional<LocationEntity> findLocationById(Long id);
        Optional<LocationEntity> findLocationByAddress(String address);
}
