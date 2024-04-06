package org.nikdev.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.nikdev.productservice.dto.request.ProductSaveDto;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.DiscountType;
import org.nikdev.productservice.entity.OrganizationEntity;
import org.nikdev.productservice.entity.ProductEntity;
import org.nikdev.productservice.mapper.ProductMapper;
import org.nikdev.productservice.repository.DiscountRepository;
import org.nikdev.productservice.repository.DiscountTypeRepository;
import org.nikdev.productservice.repository.OrganizationRepository;
import org.nikdev.productservice.repository.ProductRepository;
import org.nikdev.productservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.nikdev.productservice.constant.DiscountTypeConstant.DISCOUNT_NOT_APPLIED;
import static org.nikdev.productservice.constant.MessageConstants.Discount.DISCOUNT_NOT_FOUND;
import static org.nikdev.productservice.constant.MessageConstants.Organization.ORGANIZATION_ID_NOT_SET_ERROR;
import static org.nikdev.productservice.constant.MessageConstants.Organization.ORGANIZATION_NOT_FOUND;
import static org.nikdev.productservice.constant.MessageConstants.Product.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final OrganizationRepository organizationRepository;
    private final DiscountTypeRepository discountTypeRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndUpdateProduct(ProductSaveDto productSaveDto) throws Exception {
        ProductEntity productEntity;
        if (productSaveDto.getId() != null) {
            productEntity = productRepository.findById(productSaveDto.getId())
                    .orElseThrow(() -> new Exception(PRODUCT_NOT_FOUND));
        } else {
            productEntity = new ProductEntity();
        }
        productEntity.setName(productSaveDto.getName());
        productEntity.setDescription(productSaveDto.getDescription());
        productEntity.setPrice(productSaveDto.getPrice());
        productEntity.setQuantityStock(productSaveDto.getQuantityStock());
        //если указано, что "Скидка не применяется"
        if (productSaveDto.getDiscountType().equals(DISCOUNT_NOT_APPLIED)) {
            DiscountType discountType = discountTypeRepository.findDiscountTypeByName(DISCOUNT_NOT_APPLIED);
            DiscountEntity discountEntity = discountRepository.findById(discountType.getId())
                    .orElseThrow(() -> new Exception(DISCOUNT_NOT_FOUND));
            productEntity.setDiscount(discountEntity);
        }else{
            //проверяем наличие в БД скидки с указанным типом и промежутком дат {
            Optional<DiscountEntity> discountEntity = discountRepository.findDiscountByDiscountTypeAndDateStartBetween(productSaveDto.getDiscountType(),
                    productSaveDto.getDateStart(), productSaveDto.getDateEnd());
            //если есть уже такая скидка, то  добавляем ее для товара
            if (discountEntity.isPresent()) {
                productEntity.setDiscount(discountEntity.get());
            } else {
                DiscountEntity discount = new DiscountEntity();
                DiscountType discountType = discountTypeRepository.findDiscountTypeByName(productSaveDto.getDiscountType());
                discount.setDiscountType(discountType);
                discount.setDateStart(productSaveDto.getDateStart());
                discount.setDateEnd(productSaveDto.getDateEnd());
                discountRepository.save(discount);
                productEntity.setDiscount(discount);
            }
        }
        if (productSaveDto.getOrganizationId() != null) {
            OrganizationEntity organizationEntity = organizationRepository.findById(productSaveDto.getOrganizationId())
                    .orElseThrow(() -> new Exception(ORGANIZATION_NOT_FOUND));
            productEntity.setOrganization(organizationEntity);
        } else {
            throw new Exception(ORGANIZATION_ID_NOT_SET_ERROR);
        }
        productRepository.save(productEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductDiscountedDto> getListDiscountedProducts() {
        List<ProductEntity> productEntityList = productRepository.findAllDiscountContaining();
        return productMapper.toProductDiscountedDtoList(productEntityList);
    }
}






