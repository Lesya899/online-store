package org.nikdev.productservice.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikdev.productservice.dto.request.ProductSaveDto;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.DiscountType;
import org.nikdev.productservice.entity.OrganizationEntity;
import org.nikdev.productservice.entity.ProductEntity;
import org.nikdev.productservice.repository.DiscountRepository;
import org.nikdev.productservice.repository.DiscountTypeRepository;
import org.nikdev.productservice.repository.OrganizationRepository;
import org.nikdev.productservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.nikdev.productservice.constant.MessageConstants.Product.PRODUCT_NOT_FOUND;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final int PRODUCT_ID = 1;
    private static final int NO_EXISTING_ID = 100;
    private static final int DISCOUNT_TYPE_ID = 3;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private DiscountTypeRepository discountTypeRepository;

    @InjectMocks
    ProductServiceImpl productService;

    ProductSaveDto productSaveDto;
    DiscountType discountType;
    DiscountEntity discountEntity;
    OrganizationEntity organization;

    @BeforeEach
    void setUp() {
        productSaveDto = ProductSaveDto.builder()
                .id(PRODUCT_ID)
                .name("Какао-порошок")
                .description("Натуральный какао-порошок из элитных какао-бобов без сахара, 200 гр")
                .price(BigDecimal.valueOf(425))
                .quantityStock(8)
                .organizationId(2)
                .discountType("Периодическая")
                .dateStart(LocalDateTime.of(2024, 4, 25, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 4, 30, 0, 0))
                .build();

        discountType = DiscountType.builder()
                .id(DISCOUNT_TYPE_ID)
                .name("Периодическая")
                .discountAmount(15)
                .build();

        discountEntity = DiscountEntity.builder()
                .discountType(discountType)
                .dateStart(productSaveDto.getDateStart())
                .dateEnd(productSaveDto.getDateEnd())
                .build();

        organization = OrganizationEntity.builder()
                .name("Royal Forest")
                .description("Производит 100% натуральные и полезные продукты из кэроба и какао")
                .build();


    }

    @Test
    public void shouldReturnNotFoundProduct() {
        when(productRepository.findById(NO_EXISTING_ID)).thenReturn(null);
        productSaveDto.setId(NO_EXISTING_ID);
        Assertions.assertThrows(Exception.class, () -> productService.saveAndUpdateProduct(productSaveDto), PRODUCT_NOT_FOUND);
    }

    @Test
    void shouldSaveProduct() throws Exception {
        when(discountTypeRepository.findDiscountTypeByName(productSaveDto.getName())).thenReturn(discountType);
        when(discountRepository.findById(discountType.getId())).thenReturn(Optional.ofNullable(discountEntity));
        when(organizationRepository.findById(productSaveDto.getOrganizationId())).thenReturn(Optional.ofNullable(organization));

        ProductEntity productEntity = ProductEntity.builder()
                .name(productSaveDto.getName())
                .description(productSaveDto.getDescription())
                .price(productSaveDto.getPrice())
                .quantityStock(productSaveDto.getQuantityStock())
                .discount(discountEntity)
                .organization(organization)
                .build();
        productSaveDto.setId(null);
        productService.saveAndUpdateProduct(productSaveDto);
        verify(productRepository, times(1)).save(productEntity);
        verify(discountRepository, times(1)).findById(DISCOUNT_TYPE_ID);
        verify(organizationRepository, times(1)).findById(productSaveDto.getOrganizationId());
    }
}