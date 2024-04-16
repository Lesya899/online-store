package org.nikdev.useraccount.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikdev.entityservice.dto.TransactionEventDto;
import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserAccountOutDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.entity.Account;
import org.nikdev.useraccount.mapper.AccountMapper;
import org.nikdev.useraccount.repository.AccountRepository;
import org.nikdev.useraccount.service.CreateTransactionProducerService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.nikdev.useraccount.constant.AccountState.ACTIVE;
import static org.nikdev.useraccount.constant.AccountState.LOCKED;
import static org.nikdev.useraccount.constant.Action.UNBLOCK;
import static org.nikdev.useraccount.constant.ExceptionMessages.USER_ID_NOT_SET_ERROR;
import static org.nikdev.useraccount.constant.ExceptionMessages.USER_NOT_FOUND_ERROR;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {


    private static final int ACCOUNT_ID = 1;
    private static final int NO_EXISTING_ID = 500;
    @Mock
    AccountRepository accountRepository;
    @Spy
    AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    @Mock
    CreateTransactionProducerService createTransactionProducerService;
    @InjectMocks
    AccountServiceImpl accountService;


    @Test
    void createUserAccountTest() throws Exception {
        String userName = "afinaV";
        String email = "fin@mail.ru";
        Account account = new Account();
        account.setUserName(userName);
        account.setEmail(email);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(ACTIVE);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        accountService.createUserAccount(userName, email);
        verify(accountRepository, times(1)).save(account);
    }


    @Test
    void findByIdTest() throws Exception {
        Assertions.assertThrows(Exception.class, () -> accountService.findById(null), USER_ID_NOT_SET_ERROR);
        when(accountRepository.findById(NO_EXISTING_ID)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> accountService.findById(NO_EXISTING_ID), USER_NOT_FOUND_ERROR);

        Account account = new Account();
        account.setId(ACCOUNT_ID);
        account.setUserName("lilyaN67");
        account.setEmail("lil@mail.ru");
        account.setBalance(BigDecimal.valueOf(1500));
        account.setAccountStatus(ACTIVE);

        UserAccountOutDto expectedAccountOutDto = new UserAccountOutDto();
        expectedAccountOutDto.setUserName("lilyaN67");
        expectedAccountOutDto.setEmail("lil@mail.ru");
        expectedAccountOutDto.setBalance(BigDecimal.valueOf(1500));
        expectedAccountOutDto.setAccountStatus(ACTIVE);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(expectedAccountOutDto);
        UserAccountOutDto actualAccountOutDto = accountService.findById(ACCOUNT_ID);
        assertEquals(expectedAccountOutDto, actualAccountOutDto);
    }

    @Test
    void findEmailAddresses() {
        List<String> expectedListAddresses = Arrays.asList("antaF2@mail.ru", "lima34@mail.ru");
        when(accountRepository.findAllEmailAdresses()).thenReturn(expectedListAddresses);
        List<String> actualListAddresses = accountService.findEmailAddresses();
        assertEquals(expectedListAddresses, actualListAddresses);
    }


    @Test
    void performAction() throws Exception {
        when(accountRepository.findById(NO_EXISTING_ID)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> accountService.findById(NO_EXISTING_ID), USER_NOT_FOUND_ERROR);

        Account account = new Account();
        account.setId(ACCOUNT_ID);
        account.setAccountStatus(LOCKED);
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        ActionUserAccountDto actionUserAccountDto = new ActionUserAccountDto();
        actionUserAccountDto.setId(ACCOUNT_ID);
        actionUserAccountDto.setAction(UNBLOCK);
        accountService.performAction(actionUserAccountDto);
        assertEquals(ACTIVE, account.getAccountStatus());
        verify(accountRepository, times(1)).save(account);

    }

    @Test
    void processUserIncome() throws Exception {
        when(accountRepository.findById(NO_EXISTING_ID)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> accountService.findById(NO_EXISTING_ID), USER_NOT_FOUND_ERROR);

        UserIncomeDto userIncomeDto = new UserIncomeDto();
        userIncomeDto.setId(ACCOUNT_ID);
        userIncomeDto.setAmount(BigDecimal.valueOf(3000));

        Account account = new Account();
        account.setId(ACCOUNT_ID);
        account.setUserName("lexRT8");
        account.setEmail("lex@mail.ru");
        account.setBalance(BigDecimal.valueOf(4000));
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        TransactionEventDto transactional = new TransactionEventDto();
        transactional.setAccountId(userIncomeDto.getId());
        transactional.setCreateAt(LocalDateTime.now());
        transactional.setAmount(userIncomeDto.getAmount());
        createTransactionProducerService.sendCreateTransactionEvent(transactional);

        UserIncomeOutDto expectedUserIncomeOutDto = new UserIncomeOutDto();
        expectedUserIncomeOutDto.setUserName("lexRT8");
        expectedUserIncomeOutDto.setBalance(BigDecimal.valueOf(7000));
        when(accountMapper.toDoIncomeDto(account)).thenReturn(expectedUserIncomeOutDto);

        UserIncomeOutDto actualUserIncomeOutDto = accountService.processUserIncome(userIncomeDto);
        assertEquals(expectedUserIncomeOutDto, actualUserIncomeOutDto);

        verify(accountRepository, times(1)).findById(1);
        verify(accountRepository, times(1)).save(account);
        verify(createTransactionProducerService, times(1)).sendCreateTransactionEvent(transactional);
    }
}