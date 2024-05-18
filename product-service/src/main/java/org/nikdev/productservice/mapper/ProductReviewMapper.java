package org.nikdev.productservice.mapper;

import org.mapstruct.Mapper;

import org.nikdev.productservice.dto.response.ProductReviewDto;
import org.nikdev.productservice.entity.ProductReviewEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {

    ProductReviewDto toProductReviewDto(ProductReviewEntity productReviewEntity);

    List<ProductReviewDto> toProductReviewDtoList(List<ProductReviewEntity> productReviewEntityList);
}
