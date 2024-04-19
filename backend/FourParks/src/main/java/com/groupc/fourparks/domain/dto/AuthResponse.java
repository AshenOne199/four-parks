package com.groupc.fourparks.domain.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt"})
public record AuthResponse (String username, String message, String jwt){
}
