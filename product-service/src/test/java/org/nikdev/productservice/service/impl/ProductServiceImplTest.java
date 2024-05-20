package org.nikdev.productservice.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.nikdev.productservice.dto.request.ProductSaveDto;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.DiscountType;
import org.nikdev.productservice.entity.OrganizationEntity;
import org.nikdev.productservice.entity.ProductEntity;
import org.nikdev.productservice.mapper.ProductMapper;
import org.nikdev.productservice.repository.DiscountRepository;
import org.nikdev.productservice.repository.OrganizationRepository;
import org.nikdev.productservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.nikdev.productservice.constant.MessageConstants.Product.PRODUCT_NOT_FOUND;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Spy
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private static final int PRODUCT_ID = 1;
    private static final int NO_EXISTING_ID = 100;
    private static final int DISCOUNT_TYPE_ID = 3;
    private static final int DISCOUNT_ID = 2;
    private static final int ORGANIZATION_ID = 2;

    private ProductSaveDto productSaveDto;
    private DiscountType discountType;
    private DiscountEntity discountEntity;
    private OrganizationEntity organization;
    private ProductEntity productEntity;

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
                .id(DISCOUNT_ID)
                .discountType(discountType)
                .dateStart(productSaveDto.getDateStart())
                .dateEnd(productSaveDto.getDateEnd())
                .build();

        organization = OrganizationEntity.builder()
                .id(ORGANIZATION_ID)
                .name("Royal Forest")
                .description("Производит 100% натуральные и полезные продукты из кэроба и какао")
                .build();

        productEntity = ProductEntity.builder()
                .id(productSaveDto.getId())
                .name(productSaveDto.getName())
                .description(productSaveDto.getDescription())
                .price(productSaveDto.getPrice())
                .quantityStock(productSaveDto.getQuantityStock())
                .discount(discountEntity)
                .organization(organization)
                .reviewList(List.of())
                .ratingList(List.of())
                .build();


    }

    @Test
    public void shouldReturnNotFoundProduct() {
        when(productRepository.findById(NO_EXISTING_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> productService.saveAndUpdateProduct(productSaveDto), PRODUCT_NOT_FOUND);
    }

    @Test
    void shouldSaveProduct() throws Exception {
        productSaveDto.setId(null);
        productEntity.setId(productSaveDto.getId());

        when(discountRepository.findDiscountByTypeAndDateStartBetween(productSaveDto.getDiscountType(),
                productSaveDto.getDateStart(), productSaveDto.getDateEnd())).thenReturn(Optional.ofNullable(discountEntity));
        when(organizationRepository.findById(productSaveDto.getOrganizationId())).thenReturn(Optional.ofNullable(organization));
        productService.saveAndUpdateProduct(productSaveDto);

        verify(productRepository, times(1)).save(productEntity);
        verify(discountRepository, times(1)).findDiscountByTypeAndDateStartBetween(productSaveDto.getDiscountType(),
                productSaveDto.getDateStart(), productSaveDto.getDateEnd());
        verify(organizationRepository, times(1)).findById(productSaveDto.getOrganizationId());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        productSaveDto.setPrice(BigDecimal.valueOf(400));
        productSaveDto.setQuantityStock(12);

        when(discountRepository.findDiscountByTypeAndDateStartBetween(productSaveDto.getDiscountType(),
                productSaveDto.getDateStart(), productSaveDto.getDateEnd())).thenReturn(Optional.ofNullable(discountEntity));
        when(organizationRepository.findById(productSaveDto.getOrganizationId())).thenReturn(Optional.ofNullable(organization));
        when(productRepository.findById(productSaveDto.getId())).thenReturn(Optional.of(productEntity));

        productService.saveAndUpdateProduct(productSaveDto);

        verify(productRepository, times(1)).findById(productSaveDto.getId());
        verify(productRepository, times(1)).save(productEntity);
        verify(discountRepository, times(1)).findDiscountByTypeAndDateStartBetween(productSaveDto.getDiscountType(),
                productSaveDto.getDateStart(), productSaveDto.getDateEnd());
        verify(organizationRepository, times(1)).findById(productSaveDto.getOrganizationId());
        assertEquals(productSaveDto.getPrice(), productEntity.getPrice());
        assertEquals(productSaveDto.getQuantityStock(), productEntity.getQuantityStock());
    }

    @Test
    void shouldGetListDiscountedProducts() {
        ProductEntity productEntityTwo = ProductEntity.builder()
                .id(2)
                .name("Гранатовый соус")
                .description("Соус содержит витаминно-минеральный комплекс")
                .price(BigDecimal.valueOf(380))
                .quantityStock(4)
                .discount(discountEntity)
                .organization(organization)
                .ratingList(List.of())
                .build();

        ProductDiscountedDto productDiscountedDtoOne = ProductDiscountedDto.builder()
                .name("Какао-порошок")
                .price(BigDecimal.valueOf(425))
                .discountAmount(15)
                .build();

        ProductDiscountedDto productDiscountedDtoTwo = ProductDiscountedDto.builder()
                .name("Гранатовый соус")
                .price(BigDecimal.valueOf(380))
                .discountAmount(15)
                .build();

        List<ProductEntity> productEntityList = Arrays.asList(productEntity, productEntityTwo);
        List<ProductDiscountedDto> expectedProductEntityList = Arrays.asList(productDiscountedDtoOne, productDiscountedDtoTwo);
        when(productRepository.findAllDiscountContaining()).thenReturn(productEntityList);
        when(productMapper.toProductDiscountedDtoList(productEntityList)).thenReturn(expectedProductEntityList);

        List<ProductDiscountedDto> actualProductDiscountedDto = productService.getListDiscountedProducts();
        assertEquals(expectedProductEntityList, actualProductDiscountedDto);
    }
}