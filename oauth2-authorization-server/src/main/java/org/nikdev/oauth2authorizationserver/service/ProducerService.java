package org.nikdev.oauth2authorizationserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.nikdev.entityservice.dto.CreateAccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService {

    @Value("${spring.kafka.account.topic.create-account}")
    private String topic;

    private final KafkaTemplate<String , CreateAccountDto> kafkaTemplate;

    public void sendCreateAccountEvent(final CreateAccountDto accountEventDto) {
        final ProducerRecord<String, CreateAccountDto> record = new ProducerRecord<>(topic, String.valueOf(accountEventDto.getUserName()), accountEventDto);
        CompletableFuture<SendResult<String, CreateAccountDto>> future = kafkaTemplate.send(record);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("User details for creating an account have been sent to Kafka topic" + accountEventDto);
            }
            else {
                log.error("Error while producing", ex);
            }
        });
    }

}


