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
Great follow-up. For Kafka topic creation using NewTopic in Spring, the most commonly used and relevant attributes include:


---

1. Required Parameters

Parameter	Purpose

name	The name of the topic
numPartitions	Number of partitions
replicationFactor	Number of replicas per partition



---

2. Optional but Important Configurations

You can pass them via .configs(Map<String, String>). Here's a list of commonly used topic-level configurations:

Key	Description

min.insync.replicas	Minimum number of in-sync replicas required for a successful write
cleanup.policy	Log compaction (compact) or deletion (delete)
retention.ms	Time in ms to retain a log before deletion
segment.bytes	Segment file size
retention.bytes	Max size of a partition before old data is deleted
max.message.bytes	Maximum size of a message allowed
compression.type	Message compression (none, gzip, lz4, snappy, zstd)
delete.retention.ms	Time to retain delete markers for compaction
message.timestamp.type	CreateTime or LogAppendTime



---

Example with Multiple Configs

@Bean
public NewTopic createAdvancedTopic() {
    Map<String, String> configs = new HashMap<>();
    configs.put("min.insync.replicas", "2");
    configs.put("retention.ms", "604800000"); // 7 days
    configs.put("cleanup.policy", "delete");
    configs.put("compression.type", "gzip");

    return new NewTopic("advanced-topic", 3, (short) 2).configs(configs);
}
/*
min.insync.replicas	Minimum number of in-sync replicas required for a successful write
cleanup.policy	Log compaction (compact) or deletion (delete)
retention.ms	Time in ms to retain a log before deletion
segment.bytes	Segment file size
retention.bytes	Max size of a partition before old data is deleted
max.message.bytes	Maximum size of a message allowed
compression.type	Message compression (none, gzip, lz4, snappy, zstd)
delete.retention.ms	Time to retain delete markers for compaction
message.timestamp.type	CreateTime or LogAppendTime



---

Example with Multiple Configs
*/
@Bean
public NewTopic createAdvancedTopic() {
    Map<String, String> configs = new HashMap<>();
    configs.put("min.insync.replicas", "2");
    configs.put("retention.ms", "604800000"); // 7 days
    configs.put("cleanup.policy", "delete");
    configs.put("compression.type", "gzip");

    return new NewTopic("advanced-topic", 3, (short) 2).configs(configs);
}

