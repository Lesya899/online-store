package com.example.notificationservice.service;


import com.example.notificationservice.dto.EmailSendDto;

import java.util.List;

public interface EmailService {

    /**
     * Отправка писем о скидках
     *
     */
    void sendEmailOfDiscounts(EmailSendDto emailSendDto, List<String> listEmails) throws Exception;
}


