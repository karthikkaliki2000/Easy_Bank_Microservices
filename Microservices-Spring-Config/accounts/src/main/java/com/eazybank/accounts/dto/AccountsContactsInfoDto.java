package com.eazybank.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "accounts")
public class AccountsContactsInfoDto {

    private String message;
    private ContactInfo contactInfo;
    private List<String> onCallSupport;
    private String supportHours;
    private boolean active;

    @Data
    public static class ContactInfo {
        private String name;
        private String email;
        private String linkedin;
    }
}


//
//@ConfigurationProperties(prefix = "accounts")
//public record AccountsContactsInfoDto(String message, ContactInfo contactInfo, List<String> onCallSupport, String supportHours, boolean active) {
//    public static record ContactInfo(String name, String email, String linkedin) {}
//}
