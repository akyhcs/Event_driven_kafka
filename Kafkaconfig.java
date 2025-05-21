package com.example.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaTopicConfig is responsible for creating Kafka topics
 * during Spring Boot application startup using KafkaAdmin.
 */
@Configuration
public class KafkaTopicConfig {

    // Define your Kafka broker address here
    private static final String BOOTSTRAP_ADDRESS = "localhost:9092";

    /**
     * KafkaAdmin bean is required to create topics programmatically.
     * It uses the broker address to connect to the Kafka cluster.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        // Set the Kafka bootstrap server address
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        return new KafkaAdmin(configs);
    }

    /**
     * NewTopic bean defines a Kafka topic with specific configurations.
     * This will automatically create the topic if it does not already exist.
     * @return NewTopic instance with topic name, number of partitions, and replication factor.
     */
    @Bean
    public NewTopic createMyTopic() {
        String topicName = "my-topic";   // Replace with your topic name
        int numPartitions = 3;           // Number of partitions for the topic
        short replicationFactor = 1;     // Replication factor (1 for local dev)
        return new NewTopic(topicName, numPartitions, replicationFactor);
    }
}


