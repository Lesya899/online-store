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
    private AccountRepository accountRepository;

    @Test
    void shouldReturnAllEmailAdresses() {
        Account accountOne = Account.builder()
                .userName("lilyaN67")
                .email("lil@mail.ru")
                .balance(BigDecimal.valueOf(1500))
                .accountStatus(ACTIVE)
                .build();
        accountRepository.save(accountOne);

        Account accountTwo =Account.builder()
                .userName("alinaW")
                .email("alina@mail.ru")
                .balance(BigDecimal.valueOf(4500))
                .accountStatus(ACTIVE)
                .build();
        accountRepository.save(accountTwo);

        List<String> expectedEmailAdresses = Arrays.asList(accountOne.getEmail(), accountTwo.getEmail());
        List<String> actualEmailAdresses = accountRepository.findAllEmailAdresses();
        assertEquals(expectedEmailAdresses, actualEmailAdresses);
    }
}