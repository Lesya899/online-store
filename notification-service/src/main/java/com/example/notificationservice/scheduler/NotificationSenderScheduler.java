package com.example.notificationservice.scheduler;
import com.example.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSenderScheduler {


    private final EmailService emailService;


    @Scheduled(cron = "${notification.send-email-cron}")
    public void doWeeklyMailings() throws Exception {
        emailService.sendEmailOfDiscounts();
    }
}

