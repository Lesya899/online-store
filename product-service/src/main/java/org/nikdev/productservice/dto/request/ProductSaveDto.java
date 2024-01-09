package org.nikdev.productservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;


@Data
@Schema(description = "Данные для сохранения товара")
public class ProductSaveDto {

    @Schema(description = "ID товара")
    private Integer id;

    @Schema(description = "Наименование товара")
    private String name;

    @Schema(description = "Описание товара")
    private String description;

    @Schema(description = "Стоимость товара")
    private BigDecimal price;

    @Schema(description = "Количество товара на складе")
    private Integer quantityStock;

    @Schema(description = "Скидка на товар")
    private Integer discountId;

    @Schema(description = "Организация")
    private Integer organizationId;
}
