package com.learning.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerSimple {

    public static void main(String[] args) {

        // create producer properties
        Properties properties = new Properties();

        // old way
        // properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        // properties.setProperty("key.serializer", StringSerializer.class.getName());
        // properties.setProperty("value.serializer", StringSerializer.class.getName());

        // new way
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create a producer record
        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "hello world");

        // send data
        producer.send(record);  // asynchronous
        // producer.send(record).get();    // synchronous - block the .send() to make it synchronous - don't do this in production
        producer.flush();   // flush data
        producer.close();   // flush data and close producer
    }
}
