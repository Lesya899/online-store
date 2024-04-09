package com.example.notificationservice.service;


import com.example.notificationservice.client.ProductFeignClient;
import com.example.notificationservice.client.UserAccountFeignClient;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
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

    private static final String EMAIL_TEMPLATE_NAME = "email";
    private final String EMAIL_HEADER = "Скидки";

    private final JavaMailSender sendMailChannel;
    private final SpringTemplateEngine springTemplateEngine;
    private final UserAccountFeignClient userAccountFeignClient;
    private final ProductFeignClient productFeignClient;


    public void sendEmailOfDiscounts() throws Exception {
        List<String> listEmails = userAccountFeignClient.getEmails();
        List<ProductDiscountedDto> productDiscountedDtoList = productFeignClient.getDiscountedProducts();
        listEmails.forEach(recipient -> {
            try {
                MimeMessage mimeMessage = sendMailChannel.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                Context context = new Context();
                context.setVariable("listProducts", productDiscountedDtoList);
                String htmlContent = springTemplateEngine.process(EMAIL_TEMPLATE_NAME, context);
                message.setFrom(sender);
                message.setTo(recipient);
                message.setSubject(EMAIL_HEADER);
                message.setText(htmlContent, true);
                sendMailChannel.send(mimeMessage);
            } catch (Exception e) {
                throw new MailSendException(NOTIFICATION_COULD_NOT_BE_SENT);
            }
        });
    }
}


