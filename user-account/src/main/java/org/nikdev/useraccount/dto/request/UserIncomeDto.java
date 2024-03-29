package org.nikdev.useraccount.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "Данные для пополнения баланса пользователя")
public class UserIncomeDto {


    @Schema(description = "ID пользователя")
    private Integer userId;

    @Schema(description = "Сумма")
    private BigDecimal amount;
}
