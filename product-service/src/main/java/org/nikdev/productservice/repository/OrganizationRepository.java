package org.nikdev.productservice.repository;


import org.nikdev.productservice.entity.OrganizationEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Integer> {
}
