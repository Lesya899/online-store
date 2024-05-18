package org.nikdev.productservice.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.nikdev.productservice.dto.response.ProductDto;
import org.nikdev.productservice.entity.ProductEntity;
import org.nikdev.productservice.entity.ProductRatingEntity;


import java.util.List;

@Mapper(componentModel = "spring", uses = ProductReviewMapper.class)
public interface ProductMapper {


    @Mapping(target = "discountAmount", source = "discount.discountType.discountAmount")
    ProductDiscountedDto productDiscountedDto(ProductEntity productEntity);

    List<ProductDiscountedDto> toProductDiscountedDtoList(List<ProductEntity> productEntityList);

    @Mappings({
            @Mapping(target = "discountAmount", source = "discount.discountType.discountAmount"),
            @Mapping(target = "organizationName", source = "organization.name"),
            @Mapping(target = "productRating", source = "ratingList", qualifiedByName = "toRating"),
            @Mapping(target = "listProductReviews", source = "reviewList")

    })
    ProductDto toProductDto(ProductEntity productEntity);

    List<ProductDto> toProductDtoList(List<ProductEntity> productEntityList);

    @Named("toRating")
    default double toRating(List<ProductRatingEntity> ratingList) {
        return ratingList.stream().mapToDouble(ProductRatingEntity::getRating).average().orElse(0);
    }
}
