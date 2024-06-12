package com.oep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private long timestamp;
    private LocalDateTime expirationTime;
}
