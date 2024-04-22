package org.nikdev.productservice.repository;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.DiscountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.Optional;


@DataJpaTest
class DiscountRepositoryTest {

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    private DiscountTypeRepository discountTypeRepository;

    @Test
    void findDiscountByTypeAndDateStartBetweenTest() {
        DiscountType discountType = DiscountType.builder()
                .name("Периодическая")
                .discountAmount(15)
                .build();
        discountTypeRepository.save(discountType);

        DiscountEntity discountEntity = DiscountEntity.builder()
                .discountType(discountType)
                .dateStart(LocalDateTime.of(2024, 4, 25, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 4, 26, 0, 0))
                .build();
        discountRepository.save(discountEntity);

        Optional<DiscountEntity> actualDiscountEntity = discountRepository.findDiscountByTypeAndDateStartBetween(discountType.getName(),
                discountEntity.getDateStart(), discountEntity.getDateEnd());

        assertThat(actualDiscountEntity).isNotNull();
        actualDiscountEntity.ifPresent(value -> assertThat(value.getDiscountType().getName()).isEqualTo(discountEntity.getDiscountType().getName()));
        actualDiscountEntity.ifPresent(value -> assertThat(value.getDateStart()).isEqualTo(discountEntity.getDateStart()));
        actualDiscountEntity.ifPresent(value -> assertThat(value.getDateEnd()).isEqualTo(discountEntity.getDateEnd()));
    }
}