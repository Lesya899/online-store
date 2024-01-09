package org.nikdev.productservice.repository;

import org.nikdev.productservice.entity.DiscountEntity;
import org.springframework.data.repository.CrudRepository;

public interface DiscountRepository extends CrudRepository<DiscountEntity, Integer> {
}
