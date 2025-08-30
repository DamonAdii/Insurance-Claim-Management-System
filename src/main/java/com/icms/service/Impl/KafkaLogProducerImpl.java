package com.icms.service.Impl;

import com.icms.service.KafkaLogProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaLogProducerImpl implements KafkaLogProducer {

    private final KafkaTemplate<String,String> producerKafkaTemplate;

    @Async("loggingExecutor")
    @Override
    public void sendLog(String topic, String message) {
        producerKafkaTemplate.send(topic, message).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send log to Kafka: {}", ex.getMessage(), ex);
            } else {
                log.info("Async log sent to Kafka topic={} partition={} offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }

}
