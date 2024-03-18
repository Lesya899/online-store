package org.nikdev.productservice.service;

import org.nikdev.productservice.dto.request.ProductSaveDto;

public interface ProductService {

    /**
     * Добавление/изменение товара
     *
     */

    void saveAndUpdateProduct(ProductSaveDto productSaveDto) throws Exception;
}
