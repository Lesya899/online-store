package org.nikdev.oauth2authorizationserver.repository;

import org.nikdev.oauth2authorizationserver.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

}
