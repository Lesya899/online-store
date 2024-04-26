package org.nikdev.useraccount.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nikdev.entityservice.dto.TransactionEventDto;
import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserAccountOutDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.entity.Account;
import org.nikdev.useraccount.mapper.AccountMapper;
import org.nikdev.useraccount.repository.AccountRepository;
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

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    @Mock
    private CreateTransactionProducerService createTransactionProducerService;
    @InjectMocks
    private AccountServiceImpl accountService;

    private static final int ACCOUNT_ID = 1;
    private static final int NO_EXISTING_ID = 500;
    private  Account account;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(ACCOUNT_ID)
                .userName("lilyaN67")
                .email("lil@mail.ru")
                .balance(BigDecimal.ZERO)
                .accountStatus(ACTIVE)
                .build();
    }

    @Test
    void shouldCreateUserAccount() throws Exception {
        account.setId(null);
        accountService.createUserAccount(account.getUserName(), account.getEmail());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void shouldReturnNotFoundAccount() throws Exception {
        when(accountRepository.findById(NO_EXISTING_ID)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> accountService.findAccountById(NO_EXISTING_ID), USER_NOT_FOUND_ERROR);
    }

    @Test
    public void shouldReturnNotSetAccountId() throws Exception {
        Assertions.assertThrows(Exception.class, () -> accountService.findAccountById(null), USER_ID_NOT_SET_ERROR);
    }

    @Test
    void shouldFindAccountById() throws Exception {
        UserAccountOutDto expectedAccountOutDto = UserAccountOutDto.builder()
                .userName(account.getUserName())
                .email(account.getEmail())
                .balance(account.getBalance())
                .accountStatus(account.getAccountStatus())
                .build();
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(expectedAccountOutDto);
        UserAccountOutDto actualAccountOutDto = accountService.findAccountById(ACCOUNT_ID);
        assertEquals(expectedAccountOutDto, actualAccountOutDto);
    }

    @Test
    void shouldFindEmailAddresses() {
        List<String> expectedListAddresses = Arrays.asList("antaF2@mail.ru", "lima34@mail.ru");
        when(accountRepository.findAllEmailAdresses()).thenReturn(expectedListAddresses);
        List<String> actualListAddresses = accountService.findEmailAddresses();
        assertEquals(expectedListAddresses, actualListAddresses);
    }


    @Test
    void shouldPerformAction() throws Exception {
        account.setAccountStatus(LOCKED);
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        ActionUserAccountDto actionUserAccountDto = ActionUserAccountDto.builder()
                .id(ACCOUNT_ID)
                .action(UNBLOCK)
                .build();
        accountService.performAction(actionUserAccountDto);
        assertEquals(ACTIVE, account.getAccountStatus());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void shouldProcessUserIncome() throws Exception {
        UserIncomeDto userIncomeDto = UserIncomeDto.builder()
                .id(ACCOUNT_ID)
                .amount(BigDecimal.valueOf(3000))
                .build();

        account.setBalance(BigDecimal.valueOf(3000));
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        TransactionEventDto transactional = TransactionEventDto.builder()
                .accountId(userIncomeDto.getId())
                .createAt(LocalDateTime.now())
                .amount(userIncomeDto.getAmount())
                .build();

        createTransactionProducerService.sendCreateTransactionEvent(transactional);
        UserIncomeOutDto expectedUserIncomeOutDto = UserIncomeOutDto.builder()
                .userName(account.getUserName())
                .balance(BigDecimal.valueOf(3000))
                .build();
        when(accountMapper.toDoIncomeDto(account)).thenReturn(expectedUserIncomeOutDto);

        UserIncomeOutDto actualUserIncomeOutDto = accountService.processUserIncome(userIncomeDto);
        assertEquals(expectedUserIncomeOutDto, actualUserIncomeOutDto);
        verify(accountRepository, times(1)).findById(1);
        verify(accountRepository, times(1)).save(account);
        verify(createTransactionProducerService, times(1)).sendCreateTransactionEvent(transactional);
    }
}