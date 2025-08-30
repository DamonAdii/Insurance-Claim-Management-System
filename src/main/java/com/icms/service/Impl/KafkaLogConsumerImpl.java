package com.icms.service.Impl;

import com.icms.service.KafkaLogConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaLogConsumerImpl implements KafkaLogConsumer {

    @KafkaListener(topics = "${app.kafka.log-topic}", groupId = "icms-log-consumer-group")
    @Override
    public void consumeLog(String message) {
        // In real app, you might insert into DB or forward to ELK
        log.info("[Kafka-Log-Consumer] {}", message);
    }

}
