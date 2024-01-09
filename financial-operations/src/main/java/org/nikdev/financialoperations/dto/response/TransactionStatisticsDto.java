package org.nikdev.financialoperations.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Информация о статистике приходных и расходных операций")
public class TransactionStatisticsDto {

    @Schema(description = "Дата из указанного интервала")
    private String data;

    @Schema(description = "Общая сумма приходных операций за день")
    private BigDecimal incomeAmount;

    @Schema(description = "Общая сумма расходных операций за день")
    private BigDecimal expenseAmount;
}

