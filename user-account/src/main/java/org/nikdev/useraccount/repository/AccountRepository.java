package org.nikdev.useraccount.repository;


import org.nikdev.useraccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer> {


    @Query("select email from Account   where accountStatus = 'active'")
    List<String> findAllEmailAdresses();
}
