package org.nikdev.productservice.repository;

import org.nikdev.productservice.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, QuerydslPredicateExecutor<ProductEntity> {

    /**
     *
     * Получение всех товаров, содержащих скидку
     *
     * @return ProductEntity
     */

    @Query("select p from ProductEntity p where p.discount.discountType.discountAmount is not null")
    List<ProductEntity> findAllDiscountContaining();

}
