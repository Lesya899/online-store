package com.example.notificationservice.controller;

import com.example.notificationservice.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.notificationservice.constant.NotificationMessage.NOTIFICATION_SENT_SUCCESSFULLY;

@RestController()
@RequestMapping("/v1/notification")
@Tag(name = "NotificationController", description = "Отправка электронных писем")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;




    /**
     * Рассылка писем
     *
     * @return UserAccountOutDto
     */
    @Operation(summary = "Рассылка писем")
    @GetMapping(value = "/send-email/{header}{body}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<String> sendEmailByAllAccount(@PathVariable String header, @PathVariable String body) throws Exception {
        emailService.sendEmail(header, body);
        return new ResponseEntity<>(NOTIFICATION_SENT_SUCCESSFULLY, HttpStatus.OK);
    }
}
