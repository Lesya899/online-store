package org.nikdev.financialoperations.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Информация о финансовых операциях пользователя")
public class FinOperationOutDto {

    private LocalDateTime createdAt;
    private String type;
    private Long userId;
    private BigDecimal amount;


}
