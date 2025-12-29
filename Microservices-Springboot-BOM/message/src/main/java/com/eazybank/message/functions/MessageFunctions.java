package com.eazybank.message.functions;

import com.eazybank.message.dto.AccountsEventDto; // <-- Kafka DTO
import com.eazybank.message.dto.AccountsMsgDto;   // <-- RabbitMQ DTO
import com.eazybank.message.service.EmailService;
import com.eazybank.message.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;


@Configuration
public class MessageFunctions {

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    // Use final fields and constructor injection (BEST PRACTICE)
    private final EmailService emailService;
    private final SmsService smsService;

    // CONSTRUCTOR INJECTION: Spring automatically autowires these dependencies
    public MessageFunctions(EmailService emailService, SmsService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    // --------------------------------------------------------------------------
    // 1. RABBITMQ FLOW: email|sms (Point-to-Point Communication)
    // Destination: send-communication, Binder: rabbit
    // --------------------------------------------------------------------------

    @Bean
    Function<AccountsMsgDto, AccountsMsgDto> email() {
        return accountsMsgDto -> {
            logger.info("--- RABBITMQ PIPELINE: Executing email() function ---");

            // CALL THE EMAIL SERVICE
//            boolean success = emailService.sendEmail(accountsMsgDto);
//
//            if (!success) {
//                logger.warn("Email failed for account {}. Proceeding to next function (sms).", accountsMsgDto.accountNumber());
//            }

            // Return the DTO so it can be piped to the next function (sms)
            return accountsMsgDto;
        };
    }

    @Bean
    Function<AccountsMsgDto, Long> sms() {
        return accountsMsgDto -> {
            logger.info("--- RABBITMQ PIPELINE: Executing sms() function ---");

            // CALL THE SMS SERVICE
//            boolean success = smsService.sendSms(accountsMsgDto);
//
//            if (!success) {
//                logger.warn("SMS failed for account {}. Ending pipeline.", accountsMsgDto.accountNumber());
//            }

            // Returns the account number as the final output of the 'email|sms' pipeline
            return accountsMsgDto.accountNumber();
        };
    }

    // --------------------------------------------------------------------------
    // 2. KAFKA FLOW: processAccountEvents (Asynchronous Event Stream)
    // Destination: accounts-topic-v1, Binder: kafka
    // --------------------------------------------------------------------------

    @Bean
    // 🔥 FIXED DTO: Using AccountsEventDto as requested
    public Consumer<AccountsEventDto> processAccountEvents() {
        return event -> {
            logger.info("--------------------------------------------------");
            logger.info(" KAFKA EVENT CONSUMED: New Accounts Event");
            logger.info("Account Number: {}", event.accountNumber());
            logger.info("Event Type: {}", event.eventType());
            logger.info("--------------------------------------------------");
        };
    }
}