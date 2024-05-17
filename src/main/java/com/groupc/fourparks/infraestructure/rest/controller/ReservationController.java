package com.groupc.fourparks.infraestructure.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reservation")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://fourparks.vercel.app/")
public class ReservationController {



}
