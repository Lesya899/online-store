package org.nikdev.financialoperations.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nikdev.entityservice.dto.TransactionEventDto;
import org.nikdev.financialoperations.service.FinTransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service("transactionConsumerTransactService")
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final FinTransactionService finTransactionService;


    @KafkaListener(topics = "${spring.kafka.transaction.topic.create-transaction}", containerFactory="containerFactoryTransactionService")
    public void consume(@Payload TransactionEventDto transaction) throws Exception {
        finTransactionService.addIncomeTransaction(transaction.getUserId(),transaction.getAmount(),transaction.getCreateAt());
        log.info("User's financial transaction with id=" + transaction.getUserId()+
                " for the amount " + transaction.getAmount() + " successfully saved");
    }
}

