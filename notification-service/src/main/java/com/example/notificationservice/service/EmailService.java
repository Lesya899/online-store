package com.example.notificationservice.service;

import com.example.notificationservice.client.UserAccountFeignClient;
import com.example.notificationservice.dto.EmailSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserAccountFeignClient userAccountFeignClient;
    private final MessageChannel sendMailChannel;



    public void sendEmail(EmailSendDto emailDto) {
        List<String> listEmails = userAccountFeignClient.getEmails();
        for (String email:listEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(emailDto.getHeader());
            message.setText(emailDto.getBody());
            sendMailChannel.send(new GenericMessage<>(message));
        }
    }
}
