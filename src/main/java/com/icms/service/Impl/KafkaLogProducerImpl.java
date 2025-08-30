package com.icms.service.Impl;

import com.icms.service.KafkaLogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaLogProducerImpl implements KafkaLogProducer {

    private final KafkaTemplate<String,String> producerKafkaTemplate;

    @Async("loggingExecutor")
    @Override
    public void sendLog(String topic, String message) {
        producerKafkaTemplate.send(topic, message);
    }

}
