package org.nikdev.productservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;

@Data
@Schema(description = "Дто для совершения нечеткого поиска")
public class SearchDto {

    @Schema(description = "Поисковая строка")
    private String searchStr;

    @Schema(description = "Информация о сортировке")
    private SortingDto sorting;

    @Schema(description = "Номер страницы")
    private Integer pageNumber;

    @Schema(description = "Количество элементов на странице")
    private Integer countOnPage;

}
