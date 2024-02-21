package org.nikdev.useraccount.repository;


import org.nikdev.useraccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Integer> {
}
