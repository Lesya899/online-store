package org.nikdev.financialoperations.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Данные для получения финансовых операций пользователя")
public class FinOperationRequestDto {

    @Schema(description = "Идентификатор пользователя")
    private Integer userId;

    @Schema(description = "Номер страницы")
    private Integer pageNumber;

    @Schema(description = "Количество элементов на странице")
    private Integer countOnPage;

}
