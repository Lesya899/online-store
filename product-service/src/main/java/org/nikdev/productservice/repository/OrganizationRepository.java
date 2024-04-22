package org.nikdev.productservice.repository;


import org.nikdev.productservice.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer> {
}
