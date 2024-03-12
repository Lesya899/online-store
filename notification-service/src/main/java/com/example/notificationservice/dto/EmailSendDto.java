package com.example.notificationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO для отправки сообщений на электронную почту")
@Data
public class EmailSendDto {



    @Schema(description = "Заголовок")
    String header;

    @Schema(description = "Тело уведомления")
    String body;

}
