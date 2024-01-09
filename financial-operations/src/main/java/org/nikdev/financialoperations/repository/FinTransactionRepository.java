package org.nikdev.financialoperations.repository;

import org.nikdev.entityservice.entity.FinOperationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FinTransactionRepository extends CrudRepository<FinOperationEntity, Long> {

    List<FinOperationEntity> findAllFinOperationsByUserId(@Param("userId") Integer userId,
                                                          Pageable pageable);

    /**
     * Поулучение списка операций пользователя за указанную дату
     *
     * @param userId Long
     * @param dateStart LocalDateTime
     * @param dateEnd LocalDateTime
     */
    @Query("select f from FinOperationEntity f  where f.userId = :userId and date(f.createdAt) between :dateStart and :dateEnd")
    List<FinOperationEntity> findFinOperationsByDate(@Param("userId") Integer userId,
                                                     @Param("dateStart") LocalDateTime dateStart,
                                                     @Param("dateEnd") LocalDateTime dateEnd);
}
