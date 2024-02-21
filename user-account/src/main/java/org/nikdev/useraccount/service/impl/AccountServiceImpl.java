package org.nikdev.useraccount.service.impl;

import lombok.RequiredArgsConstructor;
import org.nikdev.entityservice.dto.TransactionEventDto;
import org.nikdev.useraccount.constant.Action;
import org.nikdev.useraccount.constant.AccountState;
import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserAccountOutDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.entity.Account;
import org.nikdev.useraccount.mapper.AccountMapper;
import org.nikdev.useraccount.repository.AccountRepository;
import org.nikdev.useraccount.service.CreateTransactionProducerService;
import org.nikdev.useraccount.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CreateTransactionProducerService createTransactionProducerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserAccount(String userName, String email) throws Exception {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(AccountState.ACTIVE);
        account.setUserName(userName);
        accountRepository.save(account);

    }

    @Override
    public UserAccountOutDto findById(Integer id) throws Exception {
        Account userAccount = accountRepository.findById(id)
                .orElseThrow(() -> new Exception("Пользователь с id " + id + " не найден"));
        return accountMapper.toDto(userAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void performAction(ActionUserAccountDto actionUserAccountDto) throws Exception {
        Account account = accountRepository.findById(actionUserAccountDto.getId())
                .orElseThrow(() -> new Exception("Пользователь с id " + actionUserAccountDto.getId() + " не найден"));
        if (actionUserAccountDto.getAction().equals(Action.DELETE)) {
            accountRepository.deleteById(actionUserAccountDto.getId());
        } else {
            if (actionUserAccountDto.getAction().equals(Action.BLOCK)) {
                account.setAccountStatus(AccountState.LOCKED);
            } else if (actionUserAccountDto.getAction().equals(Action.UNBLOCK)) {
                account.setAccountStatus(AccountState.ACTIVE);
            }
            accountRepository.save(account);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserIncomeOutDto processUserIncome(UserIncomeDto userIncomeDto) throws Exception {
        Account userAccount = accountRepository.findById(userIncomeDto.getUserId())
                .orElseThrow(() -> new Exception("Пользователь с id " + userIncomeDto.getUserId() + " не найден"));
        userAccount.setBalance(userAccount.getBalance().add(userIncomeDto.getAmount()));
        accountRepository.save(userAccount);
        TransactionEventDto transactional = new TransactionEventDto();
        transactional.setUserId(userIncomeDto.getUserId());
        transactional.setCreateAt(LocalDateTime.now());
        transactional.setAmount(userIncomeDto.getAmount());
        createTransactionProducerService.sendCreateTransactionEvent(transactional);
        return accountMapper.toDoIncomeDto(userAccount);
    }
}
