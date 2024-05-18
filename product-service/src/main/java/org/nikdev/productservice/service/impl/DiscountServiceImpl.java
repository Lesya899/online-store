package org.nikdev.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.nikdev.productservice.dto.request.DiscountSaveDto;
import org.nikdev.productservice.entity.DiscountEntity;
import org.nikdev.productservice.entity.DiscountType;
import org.nikdev.productservice.repository.DiscountRepository;
import org.nikdev.productservice.repository.DiscountTypeRepository;
import org.nikdev.productservice.service.DiscountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.nikdev.productservice.constant.MessageConstants.Discount.DISCOUNT_ALREADY_EXISTS;
import static org.nikdev.productservice.constant.MessageConstants.Discount.DISCOUNT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountTypeRepository discountTypeRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndUpdateDiscount(DiscountSaveDto discountSaveDto) throws Exception {
        Optional<DiscountEntity> discount = discountRepository.findDiscountByTypeAndDateStartBetween(discountSaveDto.getDiscountType(),
                discountSaveDto.getDateStart(), discountSaveDto.getDateEnd());
        if (discountSaveDto.getId() == null && discount.isPresent()) {
            throw new Exception(DISCOUNT_ALREADY_EXISTS);
        }
        DiscountEntity discountEntity = discountSaveDto.getId() != null? discountRepository.findById(discountSaveDto.getId())
                .orElseThrow(() -> new Exception(DISCOUNT_NOT_FOUND)): new DiscountEntity();
        DiscountType discountType = discountTypeRepository.findDiscountTypeByName(discountSaveDto.getDiscountType());
        discountEntity.setDiscountType(discountType);
        discountEntity.setDateStart(discountSaveDto.getDateStart());
        discountEntity.setDateEnd(discountSaveDto.getDateEnd());
        discountRepository.save(discountEntity);
    }
}
