package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();

        var service = new KafkaService(EmailService.class.getSimpleName(), KafkaService.topic_ecommerce_send_email, emailService::parse);
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("====================");
        System.out.println("Record: " + record.key() + ">> timestamp " + record.timestamp());
        System.out.println("Sending email...");
    }
}
