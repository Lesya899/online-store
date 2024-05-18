package org.nikdev.productservice.service;

import org.nikdev.entityservice.dto.ProductDiscountedDto;
import org.nikdev.productservice.dto.request.ProductSaveDto;
import org.nikdev.productservice.dto.request.SearchDto;
import org.nikdev.productservice.dto.response.ProductDto;

import java.util.List;

public interface ProductService {

    /**
     * Добавление/изменение товара
     *
     */

    void saveAndUpdateProduct(ProductSaveDto productSaveDto) throws Exception;


    /**
     *
     *  Получение списка товаров, на которые действует скидка
     *
     */
    List<ProductDiscountedDto> getListDiscountedProducts();


    /**
     * Получение продуктов по нечеткому поиску
     *
     * @param searchDto SearchDto
     * @return список продуктов
     */
    List<ProductDto> getProductsListBySearchStr(SearchDto searchDto) throws Exception;

}
