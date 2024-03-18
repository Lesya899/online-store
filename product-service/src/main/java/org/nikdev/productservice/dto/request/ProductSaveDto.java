package org.nikdev.productservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Schema(description = "Данные для сохранения товара")
public class ProductSaveDto {

    @Schema(description = "id товара")
    private Integer id;

    @Schema(description = "Наименование товара")
    private String name;

    @Schema(description = "Описание товара")
    private String description;

    @Schema(description = "Стоимость товара")
    private BigDecimal price;

    @Schema(description = "Количество товара на складе")
    private Integer quantityStock;

    @Schema(description = "Организация")
    private Integer organizationId;

    @Schema(description = "Тип скидки на товар")
    private String discountType;

    @Schema(description = "Дата начала действия скидки")
    private LocalDateTime dateStart;

    @Schema(description = "Дата окончания действия скидки")
    private LocalDateTime dateEnd;
}
