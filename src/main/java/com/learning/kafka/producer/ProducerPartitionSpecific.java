package com.learning.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerPartitionSpecific {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ProducerPartitionSpecific.class);

        String key = "";
        String value = "";

        // create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        int n;
        for (int i = 1; i <= 10; i++) {

            n = i % 10;
            n /= 3;

            key = "id_" + n;
            value = "hello world " + i;
            logger.info("Key: " + key + ", Value: " + value);

            // create a producer record
            // ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", key, value);
            ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", 4, key, value);

            // send data
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        logger.info("Received new metadata. \n" +
                                "Topic: " + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp() + "\n");
                    } else {
                        logger.error("Error while producing", e);
                    }
                }
            });
            producer.flush();
        }
        producer.close();
    }
}
