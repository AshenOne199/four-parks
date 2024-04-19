package com.groupc.fourparks.domain.dto.request;

import jakarta.validation.constraints.Email;

public record UserUnblockRequest(@Email String email) {
}
