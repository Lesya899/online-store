package org.nikdev.useraccount.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nikdev.entityservice.dto.CreateAccountDto;
import org.nikdev.useraccount.service.AccountService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountConsumerService {

    private final AccountService accountService;

    @KafkaListener(topics = "${spring.kafka.account.topic.create-account}", containerFactory="containerFactoryAccountService")
    public void consume(@Payload CreateAccountDto account) throws Exception {
        accountService.createUserAccount(account.getUserName(), account.getEmail());
        log.info("Account has been successfully created for the user " + account.getUserName());
    }
}
