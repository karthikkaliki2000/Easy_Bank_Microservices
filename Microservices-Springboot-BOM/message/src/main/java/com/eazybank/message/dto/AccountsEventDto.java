package com.eazybank.message.dto;

public record AccountsEventDto(
        Long accountNumber,
        String eventType, // e.g., "ACCOUNT_CREATED"
        String mobileNumber,
        String email,
        java.time.Instant timestamp
) {}