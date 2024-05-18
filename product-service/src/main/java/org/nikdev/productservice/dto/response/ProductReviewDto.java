package org.nikdev.productservice.dto.response;


import io.swagger.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация об отзывах на товар")
public class ProductReviewDto {


    @Schema(description = "Имя пользователя")
    private String userName;

    @Schema(description = "Текст отзыва")
    private String content;


}
