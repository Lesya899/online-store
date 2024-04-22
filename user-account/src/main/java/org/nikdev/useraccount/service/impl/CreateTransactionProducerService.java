package org.nikdev.useraccount.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.nikdev.entityservice.dto.TransactionEventDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateTransactionProducerService {

    @Value("${spring.kafka.transaction.topic.create-transaction}")
    private String topic;

    private final  KafkaTemplate<String , TransactionEventDto> kafkaTemplate;

    public void sendCreateTransactionEvent(TransactionEventDto transactionEventDto) {
        kafkaTemplate.send(topic, String.valueOf(transactionEventDto.getAccountId()), transactionEventDto);
        log.info("New user transaction data send to Kafka topic : "+ transactionEventDto);
        }
    }


