package com.groupc.fourparks.infraestructure.rest.controller;

import com.groupc.fourparks.application.usecase.AuditoryService;
import com.groupc.fourparks.infraestructure.model.dto.AuditoryDto;
import com.groupc.fourparks.infraestructure.model.request.AuditoryRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auditory")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://fourparks.vercel.app/")
public class AuditoryController {

    private final AuditoryService auditoryService;

    @PostMapping("/save")
    public ResponseEntity<AuditoryDto> saveAuditory(@RequestBody @Valid AuditoryRequest auditoryRequest) {
        return new ResponseEntity<>(this.auditoryService.RegisterActivity(auditoryRequest), HttpStatus.OK);
    }
}
