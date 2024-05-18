package org.nikdev.productservice.dto.request;

import lombok.Data;

@Data
public class SortingDto {

    private String fieldName;
    private String sortingDirection;
}
