package com.icms.service;

public interface KafkaLogProducer {
    public void sendLog(String topic, String message);
}
