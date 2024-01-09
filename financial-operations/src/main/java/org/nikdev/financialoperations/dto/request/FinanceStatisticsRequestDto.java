package org.nikdev.financialoperations.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Информация для  получения статистики финансовых операций")
public class FinanceStatisticsRequestDto {

    @Schema(description = "Идентификатор пользователя")
    private Integer userId;

    @Schema(description = "От какого числа формируется поиск")
    private LocalDateTime dateStart;

    @Schema(description = "До какого числа формируется поиск")
    private LocalDateTime dateEnd;
}
