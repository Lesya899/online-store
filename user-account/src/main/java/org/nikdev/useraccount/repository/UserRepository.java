package org.nikdev.useraccount.repository;

import org.nikdev.entityservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
}
