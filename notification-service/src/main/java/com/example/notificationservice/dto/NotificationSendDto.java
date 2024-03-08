package com.example.notificationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO для отправки уведомлений")
@Data
public class NotificationSendDto {

    @Schema(description = "Идентификатор уведомления")
    Integer id;

    @Schema(description = "Адрес назначения")
    String destination;

    @Schema(description = "Заголовок уведомления")
    String header;

    @Schema(description = "Тело уведомления")
    String body;

}
