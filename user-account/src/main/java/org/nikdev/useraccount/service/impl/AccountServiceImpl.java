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
import org.nikdev.useraccount.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.nikdev.useraccount.constant.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CreateTransactionProducerService createTransactionProducerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserAccount(String userName, String email) throws Exception {
        Account account = new Account();
        account.setUserName(userName);
        account.setEmail(email);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(AccountState.ACTIVE);
        accountRepository.save(account);

    }

    @Override
    public UserAccountOutDto findAccountById(Integer id) throws Exception {
        if (id == null) {
            throw new Exception(USER_ID_NOT_SET_ERROR);
        }
        Account userAccount = accountRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format(USER_NOT_FOUND_ERROR, id)));
        return accountMapper.toDto(userAccount);
    }

    @Override
    public List<String> findEmailAddresses()  {
        return accountRepository.findAllEmailAdresses();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void performAction(ActionUserAccountDto actionUserAccountDto) throws Exception {
        Account account = accountRepository.findById(actionUserAccountDto.getId())
                .orElseThrow(() -> new Exception(String.format(USER_NOT_FOUND_ERROR, actionUserAccountDto.getId())));
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
        Account userAccount = accountRepository.findById(userIncomeDto.getId())
                .orElseThrow(() -> new Exception(String.format(USER_NOT_FOUND_ERROR, userIncomeDto.getId())));
        userAccount.setBalance(userAccount.getBalance().add(userIncomeDto.getAmount()));
        accountRepository.save(userAccount);
        TransactionEventDto transactional = TransactionEventDto.builder()
                .accountId(userIncomeDto.getId())
                .createAt(LocalDateTime.now())
                .amount(userIncomeDto.getAmount())
                .build();
        createTransactionProducerService.sendCreateTransactionEvent(transactional);
        return accountMapper.toDoIncomeDto(userAccount);
    }
}
