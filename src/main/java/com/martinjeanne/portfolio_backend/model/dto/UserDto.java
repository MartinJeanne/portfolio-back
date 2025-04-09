package com.martinjeanne.portfolio_backend.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
