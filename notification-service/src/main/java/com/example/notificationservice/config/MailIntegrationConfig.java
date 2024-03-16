package com.example.notificationservice.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.MessageChannel;

import java.util.Properties;

import static org.bouncycastle.asn1.iana.IANAObjectIdentifiers.mail;


@Configuration
@EnableIntegration
@IntegrationComponentScan
public class MailIntegrationConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String protocol;



    @Bean
    public MessageChannel sendMailChannel() {
       return new DirectChannel();
    }

//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//        mailSender.setUsername(userName);
//        mailSender.setPassword(password);
//        mailSender.setProtocol("smtp");
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//        return mailSender;
//    }



    @Bean
    public IntegrationFlow sendEmailFlow() {
        return IntegrationFlow.from(sendMailChannel()) //чтобы  поток начал отправлять письма, надо отправить сообщения в канал, именно из него поток  берет сообщения.
                // Создается sendMailChannel непосредственно Spring, поскольку каналы с этим именем не настроены
                .handle(Mail.outboundAdapter(host)
                                .port(port)
                                .credentials(userName, password)
                                .protocol(protocol)
                                .javaMailProperties(p -> {
                                    p.put("mail.debug", "true");
                                    p.put("mail.smtp.starttls.enable", "true");
                                    p.put("mail.smtp.auth", "true");
                                }),
                        e -> e.id("sendMailEndpoint"))
                .get();
    }
}







