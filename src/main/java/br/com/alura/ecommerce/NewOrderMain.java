package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var dispatcher = new KafkaDispatcher()){

            var value = "Smartphone,1280.53";
            var key = UUID.randomUUID().toString();
            dispatcher.send(KafkaService.topic_ecommerce_new_order, key, value);

            var email = "Thank you for your order! We are processing it.";

            dispatcher.send(KafkaService.topic_ecommerce_send_email, key, email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
