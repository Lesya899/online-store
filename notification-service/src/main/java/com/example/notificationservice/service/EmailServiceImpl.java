package com.example.notificationservice.service;


import com.example.notificationservice.dto.EmailSendDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.*;

import static com.example.notificationservice.constant.NotificationMessage.NOTIFICATION_COULD_NOT_BE_SENT;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "email";

    private final JavaMailSender sendMailChannel;
    private final SpringTemplateEngine springTemplateEngine;


    public void sendEmailOfDiscounts(EmailSendDto emailSendDto, List<String> listEmails,
                                     List<ProductDiscountedDto> productDiscountedDtoList) throws Exception {

        listEmails.forEach(recipient -> {
            Context context = new Context(Locale.ENGLISH);
            context.setVariable("body", emailSendDto.getBody());
            context.setVariable("listProducts", productDiscountedDtoList);
            MimeMessage mimeMessage = sendMailChannel.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            try {
                message.setFrom(sender);
                message.setTo(recipient);
                message.setSubject(emailSendDto.getHeader());
                String htmlContent = springTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, context);
                message.setText(htmlContent, true);
                sendMailChannel.send(mimeMessage);
            } catch (Exception e) {
                throw new RuntimeException(NOTIFICATION_COULD_NOT_BE_SENT);
            }
        });
    }
}
