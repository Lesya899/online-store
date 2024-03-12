package org.nikdev.useraccount.repository;


import org.nikdev.useraccount.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AccountRepository extends CrudRepository<Account, Integer> {


    @Query("select email from Account  where accountStatus = 'active'")
    List<String> findAllEmailAdresses();
}
