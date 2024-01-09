package org.nikdev.productservice.repository;

import org.nikdev.productservice.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
}
