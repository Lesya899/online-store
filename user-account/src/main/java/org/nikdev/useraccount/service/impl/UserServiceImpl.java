package org.nikdev.useraccount.service.impl;

import lombok.RequiredArgsConstructor;
import org.nikdev.entityservice.dto.TransactionEventDto;
import org.nikdev.entityservice.entity.UserEntity;
import org.nikdev.useraccount.constant.Action;
import org.nikdev.useraccount.constant.UserState;

import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.dto.response.UserOutDto;
import org.nikdev.useraccount.mapper.UserMapper;
import org.nikdev.useraccount.repository.UserRepository;
import org.nikdev.useraccount.service.KafkaProducerService;
import org.nikdev.useraccount.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public UserOutDto findById(Integer id) throws Exception {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Пользователь с id " + id + " не найден"));
        return userMapper.toDto(userEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void performAction(ActionUserAccountDto actionUserAccountDto) throws Exception {
        UserEntity userEntity = userRepository.findById(actionUserAccountDto.getId())
                .orElseThrow(() -> new Exception("Пользователь с id " + actionUserAccountDto.getId() + " не найден"));
        if (actionUserAccountDto.getAction().equals(Action.DELETE)) {
            userRepository.deleteById(actionUserAccountDto.getId());
        } else {
            if (actionUserAccountDto.getAction().equals(Action.BLOCK)) {
                userEntity.setStatus(UserState.LOCKED);
            } else if (actionUserAccountDto.getAction().equals(Action.UNBLOCK)) {
                userEntity.setStatus(UserState.ACTIVE);
            }
            userRepository.save(userEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserIncomeOutDto processUserIncome(UserIncomeDto userIncomeDto) throws Exception {
        UserEntity user = userRepository.findById(userIncomeDto.getUserId())
                .orElseThrow(() -> new Exception("Пользователь с id " + userIncomeDto.getUserId() + " не найден"));
        user.setBalance(user.getBalance().add(userIncomeDto.getAmount()));
        userRepository.save(user);
        TransactionEventDto transactional = new TransactionEventDto();
        transactional.setUserId(userIncomeDto.getUserId());
        transactional.setCreateAt(LocalDateTime.now());
        transactional.setAmount(userIncomeDto.getAmount());
        kafkaProducerService.sendCreateTransactionEvent(transactional);
        return userMapper.toDoIncomeDto(user);
    }
}
