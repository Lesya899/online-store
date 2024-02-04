package org.nikdev.oauth2authorizationserver.repository;

import org.nikdev.oauth2authorizationserver.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
