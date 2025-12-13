package com.eazybank.accounts.dto;

// Example structure for the event data
public record AccountsEventDto(
        Long accountNumber,
        String eventType, // e.g., "ACCOUNT_CREATED"
        String mobileNumber,
        String email,
        java.time.Instant timestamp
) {}