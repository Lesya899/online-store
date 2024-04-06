package com.example.notificationservice.controller;

import com.example.notificationservice.client.ProductFeignClient;
import com.example.notificationservice.client.UserAccountFeignClient;
import com.example.notificationservice.dto.EmailSendDto;
import com.example.notificationservice.service.EmailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.notificationservice.constant.NotificationMessage.NOTIFICATION_SENT_SUCCESSFULLY;

@RestController()
@RequestMapping("/v1/notification")
@Tag(name = "NotificationController", description = "Отправка электронных писем")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailServiceImpl emailServiceImpl;
    private final UserAccountFeignClient userAccountFeignClient;
    private final ProductFeignClient productFeignClient;




    /**
     * Рассылка писем о скидках
     *
     * @return UserAccountOutDto
     */
    @Operation(summary = "Рассылка писем о скидках")
    @PostMapping (value = "/send-email", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<String> sendEmailByAllAccount(@RequestBody EmailSendDto emailSendDto) throws Exception {
        emailServiceImpl.sendEmailOfDiscounts(emailSendDto, userAccountFeignClient.getEmails(), productFeignClient.getDiscountedProducts());
        return new ResponseEntity<>(NOTIFICATION_SENT_SUCCESSFULLY, HttpStatus.OK);
    }
}
