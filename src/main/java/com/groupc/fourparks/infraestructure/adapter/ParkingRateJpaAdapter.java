package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.domain.model.VehicleType;
import com.groupc.fourparks.domain.port.ParkingRatePort;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingRateEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingRateDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.VehicleTypeDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ParkingRateRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingRateJpaAdapter implements ParkingRatePort{
    final private ParkingRateRepository parkingRateRepository;

    final private ParkingRateDboMapper parkingRateDboMapper;

    final private ParkingDboMapper parkingDboMapper;

    final private VehicleTypeDboMapper vehicleTypeDboMapper;
    @Override
    public ParkingRate save(ParkingRate parkingRate, Parking parking, VehicleType vehicleType) {
        var parkingRateToSave = parkingRateDboMapper.toDbo(parkingRate);
        var parkingToSave = parkingDboMapper.toDbo(parking);
        var vehicleTypeToSave = vehicleTypeDboMapper.toDbo(vehicleType);
        parkingRateToSave.setParkingId(parkingToSave);
        parkingRateToSave.setVehicleTypeId(vehicleTypeToSave);
        var parkingRateSaved = parkingRateRepository.save(parkingRateToSave);
        return parkingRateDboMapper.toDomain(parkingRateSaved);
    }
    @Override
    public ParkingRate save(ParkingRate parkingRate) {
        var parkingRateToSave = parkingRateDboMapper.toDbo(parkingRate);
        var parkingRateSaved = parkingRateRepository.save(parkingRateToSave);
        return parkingRateDboMapper.toDomain(parkingRateSaved);
    }
    @Override
    public ParkingRate getParkingRate(Long id) {
        var optionalParkingRate = parkingRateRepository.findById(id);

        if (optionalParkingRate.isEmpty()){
            throw new NotFoundException("id: " + id +" no registrado");
        }

        return parkingRateDboMapper.toDomain(optionalParkingRate.get());
    }
    @Override
    public List<ParkingRate> getParkingRatesByParking(Parking parking) {
        List<ParkingRate> allParkingRates = new ArrayList<ParkingRate>();
        List<ParkingRateEntity> ParkingRatesReceiver = parkingRateRepository.findByParkingId(parkingDboMapper.toDbo(parking));
        for (ParkingRateEntity parkingSlotEntity : ParkingRatesReceiver) {
            allParkingRates.add(parkingRateDboMapper.toDomain(parkingSlotEntity));
        }
        return allParkingRates;
    }
    @Override
    public void deleteParkingRate(ParkingRate parkingRate) {
        var parkingRateToDelete = parkingRateDboMapper.toDbo(parkingRate);
        parkingRateRepository.delete(parkingRateToDelete);
    }
}
