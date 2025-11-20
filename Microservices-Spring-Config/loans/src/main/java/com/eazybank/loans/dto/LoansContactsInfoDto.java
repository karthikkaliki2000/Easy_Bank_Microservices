package com.eazybank.loans.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

//@ConfigurationProperties(prefix = "loans")
//public record LoansContactsInfoDto(String message, ContactInfo contactInfo, List<String> onCallSupport, String supportHours, boolean active) {
//    public static record ContactInfo(String name, String email, String linkedin) {}
//}


import lombok.Data;


@Data
@ConfigurationProperties(prefix = "loans")
public class LoansContactsInfoDto {

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
