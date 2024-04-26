package org.nikdev.productservice.repository;

import org.junit.jupiter.api.Test;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.DiscountType;
import org.nikdev.productservice.entity.OrganizationEntity;
import org.nikdev.productservice.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nikdev.productservice.constant.DiscountTypeConstant.DISCOUNT_NOT_APPLIED;


@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountTypeRepository discountTypeRepository;


    @Test
    void shouldFindAllDiscountContainingTest() {
        DiscountType discountTypeOne = DiscountType.builder()
                .name("Периодическая")
                .discountAmount(15)
                .build();
        discountTypeRepository.save(discountTypeOne);

        DiscountEntity discountEntityOne = DiscountEntity.builder()
                .discountType(discountTypeOne)
                .dateStart(LocalDateTime.of(2024, 4, 25, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 4, 26, 0, 0))
                .build();
        discountRepository.save(discountEntityOne);

        OrganizationEntity organization = OrganizationEntity.builder()
                .name("Royal Forest")
                .description("Производит 100% натуральные и полезные продукты из кэроба и какао")
                .build();
        organizationRepository.save(organization);

        ProductEntity productEntityOne = ProductEntity.builder()
                .name("Какао-порошок")
                .description("Натуральный какао-порошок из элитных какао-бобов без сахара, 200 гр")
                .price(BigDecimal.valueOf(425))
                .quantityStock(8)
                .discount(discountEntityOne)
                .organization(organization)
                .build();
        productRepository.save(productEntityOne);

        DiscountType discountTypeTwo = DiscountType.builder()
                .name(DISCOUNT_NOT_APPLIED)
                .build();
        discountTypeRepository.save(discountTypeTwo);

        DiscountEntity discountEntityTwo = DiscountEntity.builder()
                .discountType(discountTypeTwo)
                .build();
        discountRepository.save(discountEntityTwo);

        ProductEntity productEntityTwo = ProductEntity.builder()
                .name("Веганский молочный шоколад")
                .description("Состоит из натуральных ингредиентов, не содержит ГМО и вредных добавок")
                .price(BigDecimal.valueOf(288))
                .quantityStock(12)
                .discount(discountEntityTwo)
                .organization(organization)
                .build();
        productRepository.save(productEntityTwo);

        List<ProductEntity> expectedList = List.of(productEntityOne);
        List<ProductEntity> actualList = productRepository.findAllDiscountContaining();
        assertEquals(expectedList, actualList);
    }
}