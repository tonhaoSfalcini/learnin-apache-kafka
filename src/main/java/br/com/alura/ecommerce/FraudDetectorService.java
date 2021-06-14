package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();

        var kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName(), KafkaService.topic_ecommerce_new_order, fraudService::parse);
        kafkaService.run();

    }

    private void parse(ConsumerRecord<String, String> record){
        System.out.println("====================");
        System.out.println("Record: " + record.key() + ">> timestamp " + record.timestamp());
        System.out.println("Processing new order...");
        System.out.println("Checking for fraud");
        System.out.println("Order processed");
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        return properties;
    }
}
