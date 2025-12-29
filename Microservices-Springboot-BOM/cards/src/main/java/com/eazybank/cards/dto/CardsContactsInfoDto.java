package com.eazybank.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "cards")
public class CardsContactsInfoDto {

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


//@ConfigurationProperties(prefix = "cards")
//public record CardsContactsInfoDto(String message, ContactInfo contactInfo, List<String> onCallSupport, String supportHours, boolean active) {
//    public static record ContactInfo(String name, String email, String linkedin) {}
//}
