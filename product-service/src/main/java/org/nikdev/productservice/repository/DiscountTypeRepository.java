package org.nikdev.productservice.repository;


import org.nikdev.productservice.entity.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountTypeRepository extends JpaRepository<DiscountType, Integer> {

    /**
     *  Получение типа скидки по названию
     *
     * @return DiscountType
     */
    DiscountType findDiscountTypeByName(String name);
}
