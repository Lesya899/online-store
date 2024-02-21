package org.nikdev.useraccount.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nikdev.entityservice.dto.CreateAccountDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service("accountConsumerService")
@RequiredArgsConstructor
@Slf4j
public class AccountConsumerService {

    private final AccountService accountService;

    @KafkaListener(topics = "${spring.kafka.account.topic.create-account}", containerFactory="containerFactoryAccountService")
    public void consume(@Payload CreateAccountDto account) throws Exception {
        accountService.addUserAccount(account.getUserName(), account.getEmail());
        log.info("Account has been successfully created for the user");
    }
}
