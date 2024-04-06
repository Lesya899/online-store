package org.nikdev.entityservice.dto;



import io.swagger.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;


@Data
@Schema(description = "Информация о товарах, на которые действует скидка")
public class ProductDiscountedDto {

    @Schema(description = "Наименование товара")
    private String name;

    @Schema(description = "Цена товара")
    private BigDecimal price;

    @Schema(description = "Размер скидки в %")
    private Integer discountAmount;

}

