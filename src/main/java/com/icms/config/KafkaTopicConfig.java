package com.icms.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderLogsTopic() {
        return TopicBuilder.name("icms-logs")  // same as in application.yml
                .partitions(3)                  // number of partitions
                .replicas(1)                    // replication factor (keep 1 for local dev)
                .build();
    }

}
