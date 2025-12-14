package com.eazybank.message.service;

import com.eazybank.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Use constructor injection
    private final JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    // Constructor Injection
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends an account creation confirmation email to the customer.
     * * @param accountsMsgDto The DTO containing account, name, and email details.
     * @return true if the email was successfully sent, false otherwise.
     */
    public boolean sendEmail(AccountsMsgDto accountsMsgDto) {
        logger.info("--- Attempting to send email to {} for account: {}",
                accountsMsgDto.email(), accountsMsgDto.accountNumber());

        try {
            // 1. Create a simple mail message object
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // 2. Set the mail properties
            String recipientEmail = accountsMsgDto.email();
            String subject = "🎉 Welcome, " + accountsMsgDto.name() + "! Your EazyBank Account is Ready - Ref: " + accountsMsgDto.accountNumber();
            String content = "Dear " + accountsMsgDto.name() + ",\n\n" +
                    "We are delighted to inform you that your new EazyBank account has been successfully created.\n\n" +
                    "🏦 Account Details:\n" +
                    "   Account Number: " + accountsMsgDto.accountNumber() + "\n" +
                    "   Mobile Number: " + accountsMsgDto.mobileNumber() + "\n\n" +
                    "Please log in to our secure portal to manage your finances.\n\n" +
                    "Thank you for choosing EazyBank!";

            mailMessage.setTo(recipientEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(content);
            mailMessage.setFrom("karthikkaliki2000@gmail.com");


            // 3. Send the email
            javaMailSender.send(mailMessage);

            logger.info("Successfully SENT email to {} for account {}", recipientEmail, accountsMsgDto.accountNumber());
            return true;

        } catch (Exception e) {
            logger.error("!!! FAILED to send email for account {}. Error: {}", accountsMsgDto.accountNumber(), e.getMessage());
            logger.error("Email exception stack trace: ", e);
            return false;
        }
    }
}