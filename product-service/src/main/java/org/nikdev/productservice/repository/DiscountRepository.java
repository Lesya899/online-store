package org.nikdev.productservice.repository;

import org.nikdev.productservice.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Integer> {


    /**
     * Получение скидки по типу скидки и промежутку дат
     *
     * @return DiscountEntity
     */
    @Query("select d from DiscountEntity d where d.discountType.name = :discountType and " +
            "d.dateStart = :dateStart and d.dateEnd = :dateEnd")
    Optional<DiscountEntity> findDiscountByTypeAndDateStartBetween(@Param("discountType") String discountType,
                                                                          @Param("dateStart") LocalDateTime dateStart,
                                                                          @Param("dateEnd") LocalDateTime dateEnd);


    /**
     * Получение скидки id типа скидки
     *
     * @return DiscountEntity
     */
    @Query("select d from DiscountEntity d where d.discountType.id = :id")
    Optional<DiscountEntity> findByDiscountTypeId(@Param("id") Integer id);
}
