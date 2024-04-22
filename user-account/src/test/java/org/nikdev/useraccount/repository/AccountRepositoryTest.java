package org.nikdev.useraccount.repository;

import org.junit.jupiter.api.Test;
import org.nikdev.useraccount.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nikdev.useraccount.constant.AccountState.ACTIVE;


@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void findAllEmailAdressesTest() {
        Account accountOne = new Account();
        accountOne.setUserName("lilyaN67");
        accountOne.setEmail("lil@mail.ru");
        accountOne.setBalance(BigDecimal.valueOf(1500));
        accountOne.setAccountStatus(ACTIVE);
        accountRepository.save(accountOne);

        Account accountTwo = new Account();
        accountTwo.setUserName("alinaW");
        accountTwo.setEmail("alina@mail.ru");
        accountTwo.setBalance(BigDecimal.valueOf(4500));
        accountTwo.setAccountStatus(ACTIVE);
        accountRepository.save(accountTwo);

        List<String> expectedEmailAdresses = Arrays.asList(accountOne.getEmail(), accountTwo.getEmail());
        List<String> actualEmailAdresses = accountRepository.findAllEmailAdresses();
        assertEquals(expectedEmailAdresses, actualEmailAdresses);
    }
}