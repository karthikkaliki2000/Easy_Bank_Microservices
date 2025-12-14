package com.eazybank.message.service;

import com.eazybank.message.dto.AccountsMsgDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    // --- Twilio Configuration (Injected from application.yml) ---
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    // Using MessagingServiceSid is often preferred for dynamic sending
    @Value("${twilio.messagingServiceSid}")
    private String messagingServiceSid;

    @Value("${twilio.fromNumber}")
    private String fromNumber;


    @PostConstruct
    public void init() {
        try {
            Twilio.init(accountSid, authToken);
            logger.info("Twilio client initialized successfully.");
        } catch (Exception e) {
            logger.error("!!! FATAL: Failed to initialize Twilio client.", e);
            // Consider throwing a runtime exception if initialization failure should stop the app
        }
    }

    /**
     * Sends an account creation confirmation SMS using the Twilio API.
     * @param accountsMsgDto The DTO containing account and mobile number details.
     * @return true if the SMS was successfully accepted by Twilio, false otherwise.
     */
    public boolean sendSms(AccountsMsgDto accountsMsgDto) {

        String mobileNumber = accountsMsgDto.mobileNumber();
        String bankingAccountNumber = String.valueOf(accountsMsgDto.accountNumber()); // Use the correct account number
        String smsBody = "Welcome " + accountsMsgDto.name() +
                "! Your EazyBank account " + bankingAccountNumber +
                " is created. Log in to access services.";


        logger.info("--- Attempting to send SMS via Twilio to mobile: {}", mobileNumber);

        try {
            // 1. Create the Message payload
            Message message = Message.creator(
                    new PhoneNumber(mobileNumber),
                    messagingServiceSid,
                    smsBody
            ).create();

            // 2. Log success and the message SID (for audit)

            logger.info("Successfully SENT SMS to {} for account {}", mobileNumber, bankingAccountNumber);
            return true;

        } catch (Exception e) {
            logger.error("!!! FAILED to send SMS via Twilio for account {}. Error: {}", bankingAccountNumber, e.getMessage());
            logger.error("Twilio exception stack trace: ", e);
            return false;
        }
    }
}