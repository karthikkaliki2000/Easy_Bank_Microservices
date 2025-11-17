package com.eazybank.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactsInfoDto(String message, ContactInfo contactInfo, List<String> onCallSupport, String supportHours, boolean active) {
    public static record ContactInfo(String name, String email, String linkedin) {}
}

