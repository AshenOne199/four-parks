package com.groupc.fourparks.infraestructure.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupc.fourparks.application.service.ParkingSlotServiceImpl;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;
import com.groupc.fourparks.infraestructure.model.request.SlotToShow;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/slots")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://fourparks.vercel.app/")
public class ParkingSlotController {
    private final ParkingSlotServiceImpl parkingSlotServiceImpl;

    @PostMapping("/slot/new")
    public ResponseEntity<SlotToShow> newSlot(@RequestBody @Valid ParkingSlotRequest parkingSlotRequest){
        return new ResponseEntity<>(this.parkingSlotServiceImpl.newParkingSlot(parkingSlotRequest), HttpStatus.OK);
    }

    @GetMapping("/slot/id/{id}")
    public ResponseEntity<SlotToShow> getSlot(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingSlotServiceImpl.getParkingSlot(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/parking/id/{id}")
    public ResponseEntity<List<SlotToShow>> getSlotsByParking(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingSlotServiceImpl.getParkingSlotsByParking(Long.parseLong(id)), HttpStatus.OK);
    }

    @DeleteMapping("/slot/delete/{id}")
    public String deleteSlot(@PathVariable(required = true) String id) {
        
        return this.parkingSlotServiceImpl.deleteParkingSlot(Long.parseLong(id));
    }

    @PutMapping("/slot/update")
    public ResponseEntity<SlotToShow> modifySlot(@RequestBody @Valid ParkingSlotRequest parkingSlotRequest){
        System.out.println(parkingSlotRequest.getId());
        return new ResponseEntity<>(this.parkingSlotServiceImpl.modifyParkingSlot(parkingSlotRequest), HttpStatus.OK);
    }
}
