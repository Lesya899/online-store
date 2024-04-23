package org.nikdev.productservice.service.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.nikdev.productservice.constant.MessageConstants.Discount.DISCOUNT_NOT_FOUND;


@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

    private static final int DISCOUNT_ID = 1;
    private static final int NO_EXISTING_ID = 10;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private DiscountTypeRepository discountTypeRepository;

    @InjectMocks
    DiscountServiceImpl discountService;

    DiscountType discountType;

    DiscountSaveDto discountSaveDto;

    @BeforeEach
    public void setup() {
        discountType = DiscountType.builder()
                .name("Праздничная")
                .discountAmount(15)
                .build();

        discountSaveDto = DiscountSaveDto.builder()
                .id(DISCOUNT_ID)
                .discountType(discountType.getName())
                .dateStart(LocalDateTime.of(2024, 4, 29, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 5, 1, 0, 0))
                .build();
    }


    @Test
    public void shouldReturnNotFound(){
        when(discountRepository.findById(NO_EXISTING_ID)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> discountService.saveAndUpdateDiscount(discountSaveDto), DISCOUNT_NOT_FOUND);
    }


    @Test
    void shouldSaveDiscount() throws Exception {
        when(discountTypeRepository.findDiscountTypeByName(discountType.getName())).thenReturn(discountType);

        discountSaveDto.setId(null);
        discountService.saveAndUpdateDiscount(discountSaveDto);

        DiscountEntity discountEntity = DiscountEntity.builder()
                .discountType(discountType)
                .dateStart(discountSaveDto.getDateStart())
                .dateEnd(discountSaveDto.getDateEnd())
                .build();
        verify(discountRepository, times(1)).save(discountEntity);
    }

    @Test
    void shouldUpdateDiscount() throws Exception {
        DiscountEntity discountEntity = DiscountEntity.builder()
                .id(DISCOUNT_ID)
                .discountType(discountType)
                .dateStart(LocalDateTime.of(2024, 4, 29, 0, 0))
                .dateEnd(LocalDateTime.of(2024, 5, 1, 0, 0))
                .build();
        when(discountTypeRepository.findDiscountTypeByName(discountType.getName())).thenReturn(discountType);
        when(discountRepository.findById(DISCOUNT_ID)).thenReturn(Optional.ofNullable(discountEntity));

        discountSaveDto.setDateEnd(LocalDateTime.of(2024, 5, 4, 0, 0));
        discountService.saveAndUpdateDiscount(discountSaveDto);

        verify(discountRepository, times(1)).findById(DISCOUNT_ID);
        verify(discountRepository, times(1)).save(discountEntity);
        assertEquals(discountSaveDto.getDiscountType(), discountEntity.getDiscountType().getName());
        assertEquals(discountSaveDto.getDateStart(), discountEntity.getDateStart());
        assertEquals(discountSaveDto.getDateEnd(), discountEntity.getDateEnd());
    }
}