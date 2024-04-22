package org.nikdev.productservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikdev.productservice.dto.request.DiscountSaveDto;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.DiscountType;
import org.nikdev.productservice.repository.DiscountRepository;
import org.nikdev.productservice.repository.DiscountTypeRepository;


import java.time.LocalDateTime;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private DiscountTypeRepository discountTypeRepository;

    @InjectMocks
    DiscountServiceImpl discountService;

    @Test
    void saveDiscountTest() throws Exception {
        DiscountType discountType = DiscountType.builder()
                .name("Разовая")
                .discountAmount(15)
                .build();
        when(discountTypeRepository.findDiscountTypeByName(discountType.getName())).thenReturn(discountType);

        DiscountSaveDto discountSaveDto = DiscountSaveDto.builder()
                .discountType(discountType.getName())
                .dateStart(LocalDateTime.of(2024, 4, 25, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 4, 26, 0, 0))
                .build();
        discountService.saveAndUpdateDiscount(discountSaveDto);

        DiscountEntity discountEntity = DiscountEntity.builder()
                .discountType(discountType)
                .dateStart(discountSaveDto.getDateStart())
                .dateEnd(discountSaveDto.getDateEnd())
                .build();
        verify(discountRepository, times(1)).save(discountEntity);

    }
}