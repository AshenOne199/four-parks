package com.groupc.fourparks.api.login.controller.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record AuthCreateRoleRequest(@Size(max = 3, message = "El usuario no puede tener más de tres roles") List<String> roleListName) {
}
