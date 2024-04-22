package org.nikdev.productservice.repository;

import org.junit.jupiter.api.Test;
import org.nikdev.productservice.entity.DiscountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class DiscountTypeRepositoryTest {

    @Autowired
    DiscountTypeRepository discountTypeRepository;

    @Test
    void findDiscountTypeByName() {
        DiscountType expectedDiscountType = DiscountType.builder()
                .name("Разовая")
                .discountAmount(20)
                .build();
        discountTypeRepository.save(expectedDiscountType);
        DiscountType actualDiscountType = discountTypeRepository.findDiscountTypeByName(expectedDiscountType.getName());
        assertEquals(expectedDiscountType, actualDiscountType);
    }
}