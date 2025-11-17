package com.eazybank.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "cards")
public record CardsContactsInfoDto(String message, ContactInfo contactInfo, List<String> onCallSupport, String supportHours, boolean active) {
    public static record ContactInfo(String name, String email, String linkedin) {}
}