package org.nikdev.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.nikdev.productservice.constant.ExceptionMessages.*;
import org.nikdev.productservice.dto.request.ProductSaveDto;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.OrganizationEntity;
import org.nikdev.productservice.entity.ProductEntity;
import org.nikdev.productservice.repository.DiscountRepository;
import org.nikdev.productservice.repository.OrganizationRepository;
import org.nikdev.productservice.repository.ProductRepository;
import org.nikdev.productservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(ProductSaveDto productSaveDto) throws Exception {
        ProductEntity productEntity;
        if (productSaveDto.getId() != null) {
            productEntity = productRepository.findById(productSaveDto.getId())
                    .orElseThrow(() -> new Exception(ProductExceptions.PRODUCT_NOT_FOUND));
        } else {
            productEntity = new ProductEntity();
        }
        productEntity.setName(productSaveDto.getName());
        productEntity.setDescription(productSaveDto.getDescription());
        productEntity.setPrice(productSaveDto.getPrice());
        productEntity.setQuantityStock(productSaveDto.getQuantityStock());
        if (productSaveDto.getDiscountId() != null) {
            DiscountEntity discountEntity = discountRepository.findById(productSaveDto.getDiscountId())
                    .orElseThrow(() -> new Exception(DiscountExceptions.DISCOUNT_NOT_FOUND));
            productEntity.setDiscount(discountEntity);
        } else {
            productEntity.setDiscount(null);
        }
        if (productSaveDto.getOrganizationId() != null) {
            OrganizationEntity organizationEntity = organizationRepository.findById(productSaveDto.getOrganizationId())
                    .orElseThrow(() -> new Exception(OrganizationExceptions.ORGANIZATION_NOT_FOUND));
            productEntity.setOrganization(organizationEntity);
        } else {
            throw new Exception(OrganizationExceptions.ORGANIZATION_ID_NOT_SET_ERROR);
        }
        productRepository.save(productEntity);
    }
}






