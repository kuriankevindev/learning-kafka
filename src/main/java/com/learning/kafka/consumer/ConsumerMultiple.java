package com.learning.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerMultiple {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ConsumerMultiple.class);

        // create consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-app");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create consumer
        KafkaConsumer<String, String> consumer1 = new KafkaConsumer<>(properties);
        KafkaConsumer<String, String> consumer2 = new KafkaConsumer<>(properties);
        KafkaConsumer<String, String> consumer3 = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer1.subscribe(Collections.singleton("first_topic"));
        consumer2.subscribe(Collections.singleton("first_topic"));
        consumer3.subscribe(Collections.singleton("first_topic"));

        // poll for new data
        while(true) {
            ConsumerRecords<String, String> records1 = consumer1.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records1) {
                logger.info("Key: " + record.key() + "\n" +
                        "Value: " + record.value() + "\n" +
                        "Partition: " + record.partition() + "\n" +
                        "Offset: " + record.offset());
            }
            ConsumerRecords<String, String> records2 = consumer2.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records2) {
                logger.info("Key: " + record.key() + "\n" +
                        "Value: " + record.value() + "\n" +
                        "Partition: " + record.partition() + "\n" +
                        "Offset: " + record.offset());
            }
            ConsumerRecords<String, String> records3 = consumer3.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records3) {
                logger.info("Key: " + record.key() + "\n" +
                        "Value: " + record.value() + "\n" +
                        "Partition: " + record.partition() + "\n" +
                        "Offset: " + record.offset());
            }
        }
    }
}
