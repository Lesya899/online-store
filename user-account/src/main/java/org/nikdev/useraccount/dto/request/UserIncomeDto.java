package org.nikdev.useraccount.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Данные для пополнения баланса пользователя")
public class UserIncomeDto {


    @Schema(description = "ID аккаунта")
    private Integer id;

    @Schema(description = "Сумма")
    private BigDecimal amount;
}
