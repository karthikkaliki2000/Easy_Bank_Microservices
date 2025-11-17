package com.eazybank.accounts;

import com.eazybank.accounts.dto.AccountsContactsInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts Microservice REST API Documentation",
                description = "EazyBank Accounts Microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Karthik Kaliki",
                        email = "karthikkaliki2000@gmail.com",
                        url = "http://karthik.com/url"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://karthik.com/url"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "EazyBank Account Microservice REST API External Docs",
                url = "http://karthik.com/url"
        )
)
@EnableConfigurationProperties(value = {AccountsContactsInfoDto.class})
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);


    }
}
