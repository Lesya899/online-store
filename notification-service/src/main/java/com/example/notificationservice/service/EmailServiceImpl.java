package com.example.notificationservice.service;


import com.example.notificationservice.dto.EmailSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    @Value("${spring.mail.username}")
    private String sender;

    private final MessageChannel sendMailChannel;




    public void sendEmailOfDiscounts(EmailSendDto emailSendDto, List<String> listEmails)  {
        for (String email:listEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(email);
            message.setSubject(emailSendDto.getHeader());
            message.setText(emailSendDto.getBody());
            sendMailChannel.send(new GenericMessage<>( message));
        }
    }
}
