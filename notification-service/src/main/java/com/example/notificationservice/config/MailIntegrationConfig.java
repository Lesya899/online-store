package com.example.notificationservice.config;


import com.example.notificationservice.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;


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



    @Bean
    public MessageChannel sendMailChannel() {
        return new DirectChannel();
    }





    @Bean
    public IntegrationFlow sendEmailFlow() {
        return IntegrationFlow.from(sendMailChannel()) //чтобы  поток начал отправлять письма, надо отправить сообщения в канал, именно из него поток  берет сообщения.
                // Создается sendMailChannel непосредственно Spring, поскольку каналы с этим именем не настроены
                .handle(Mail.outboundAdapter(host)
                        .port(port)
                        .credentials(userName, password)
                        .protocol("smtp")
                        .javaMailProperties(p -> {
                            p.put("mail.debug", "true");
                            p.put("mail.smtp.ssl.trust", "*");
                            p.put("mail.smtp.starttls.enable", "true");
                        }),
                        e -> e.id("sendMailEndpoint"))
                .get();
    }
}






