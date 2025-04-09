package com.martinjeanne.portfolio_backend.model.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {
    private final String token;
    private final String tokenType = "Bearer";

    public AuthResponseDto(String accessToken) {
        this.token = accessToken;
    }
}
