package org.nikdev.productservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Schema(description = "Данные для сохранения скидки")
public class DiscountSaveDto {

    @Schema(description = "ID скидки")
    private Integer id;

    @Schema(description = "Размер скидки")
    private Integer discountAmount;

    @Schema(description = "Дата начала действия скидки")
    private LocalDateTime dateStart;

    @Schema(description = "Дата окончания действия скидки")
    private LocalDateTime dateEnd;
}
