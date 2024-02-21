package org.nikdev.oauth2authorizationserver.repository;

import org.nikdev.oauth2authorizationserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

}
