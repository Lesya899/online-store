package org.nikdev.productservice.dto.response;

import io.swagger.oas.annotations.media.Schema;
import lombok.Data;
import org.nikdev.productservice.entity.ProductReviewEntity;


import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "Информация о товарах")
public class ProductDto {

    @Schema(description = "Наименование товара")
    private String name;

    @Schema(description = "Описание товара")
    private String description;

    @Schema(description = "Цена")
    private BigDecimal price;

    @Schema(description = "Количество товара на складе")
    private Integer quantityStock;

    @Schema(description = "Размер скидки в %")
    private Integer discountAmount;

    @Schema(description = "Название организации")
    private String organizationName;

    @Schema(description = "Рейтинг товара")
    private double productRating;

    @Schema(description = "Отзывы на товар")
    private List<ProductReviewDto> listProductReviews;

}
