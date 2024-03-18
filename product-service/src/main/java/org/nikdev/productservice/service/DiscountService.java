package org.nikdev.productservice.service;

import org.nikdev.productservice.dto.request.DiscountSaveDto;


public interface DiscountService {

    /**
     * Добавление/изменение скидки
     *
     */

    void saveAndUpdateDiscount(DiscountSaveDto discountSaveDto) throws Exception;
}
