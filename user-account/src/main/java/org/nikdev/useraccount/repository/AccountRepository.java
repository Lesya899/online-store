package org.nikdev.useraccount.repository;


import org.nikdev.useraccount.entity.Account;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, Integer> {
}
