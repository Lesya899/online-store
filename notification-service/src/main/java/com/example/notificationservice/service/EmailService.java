package com.example.notificationservice.service;


import com.example.notificationservice.dto.EmailSendDto;
import org.nikdev.entityservice.dto.ProductDiscountedDto;

import java.util.List;

public interface EmailService {

    /**
     * Отправка писем о скидках
     *
     */
    void sendEmailOfDiscounts(EmailSendDto emailSendDto, List<String> listEmails,
                              List<ProductDiscountedDto> productDiscountedDtoList) throws Exception;
}


