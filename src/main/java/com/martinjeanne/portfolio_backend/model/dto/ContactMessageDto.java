package com.martinjeanne.portfolio_backend.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ContactMessageDto {
    @NotNull(message = "Author is required")
    private String author;
    @NotNull(message = "Message is required")
    private String message;
}
