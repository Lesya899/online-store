package org.nikdev.oauth2authorizationserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nikdev.entityservice.dto.CreateAccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateAccountProducerService {

    @Value("${spring.kafka.account.topic.create-account}")
    private String topic;

    private final KafkaTemplate<String , CreateAccountDto> kafkaTemplate;

    public void sendCreateAccountEvent(CreateAccountDto accountEventDto) {
        kafkaTemplate.send(topic, String.valueOf(accountEventDto.getUserName()), accountEventDto);
        log.info("User details for creating an account have been sent to Kafka topic");
    }
}

