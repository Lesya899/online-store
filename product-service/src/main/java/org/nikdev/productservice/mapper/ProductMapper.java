package org.nikdev.productservice.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.nikdev.productservice.entity.ProductEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "discountAmount", source = "discount.discountType.discountAmount")
    ProductDiscountedDto productDiscountedDto(ProductEntity productEntity);
    List<ProductDiscountedDto> toProductDiscountedDtoList(List<ProductEntity> productEntityList);



}
