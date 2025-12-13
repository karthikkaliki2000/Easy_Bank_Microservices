package com.eazybank.message.functions;

import com.eazybank.message.dto.AccountsEventDto;
import com.eazybank.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;


@Configuration
public class MessageFunctions {

    private static final Logger logger= LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
   Function<AccountsMsgDto,AccountsMsgDto> email(){
       return accountsMsgDto -> {
           logger.info("Sent Email with these details: "+accountsMsgDto.toString());
           return accountsMsgDto;
       };
   }

    @Bean
    Function<AccountsMsgDto,Long> sms(){
        return accountsMsgDto -> {
            logger.info("Sent SMS with these details: "+accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
        };
    }


    @Bean
    public Consumer<AccountsEventDto> processAccountEvents() {
        return event -> {
            logger.info("--------------------------------------------------");
            logger.info(" KAFKA EVENT CONSUMED: New Accounts Event");
            logger.info("Account Number: {}", event.accountNumber());
            logger.info("Event Type: {}", event.eventType());
            logger.info("--------------------------------------------------");

            // POC Logic: Logging successful event consumption for auditing/analytics purposes
        };
    }
}
