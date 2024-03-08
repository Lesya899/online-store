package com.example.notificationservice.adapter;


import com.example.notificationservice.dto.NotificationSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static com.example.notificationservice.constant.NotificationExceptionMessage.NOTIFICATION_COULD_NOT_BE_SENT;

@Component
@RequiredArgsConstructor
public class NotificationAdapterEmail implements NotificationAdapter {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(NotificationSendDto notificationSendDto) throws Exception {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(notificationSendDto.getDestination());
            mailMessage.setSubject(notificationSendDto.getHeader());
            mailMessage.setText(notificationSendDto.getBody());
            mailMessage.setFrom(from);
            LocalDateTime time = LocalDateTime.now();
            mailMessage.setSentDate(Date.from(Instant.from(time)));
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new Exception(NOTIFICATION_COULD_NOT_BE_SENT);
        }
    }

}
