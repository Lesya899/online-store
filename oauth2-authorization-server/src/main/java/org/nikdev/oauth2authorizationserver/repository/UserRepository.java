package org.nikdev.oauth2authorizationserver.repository;

import org.nikdev.oauth2authorizationserver.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

}
