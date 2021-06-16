package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<String>()) {

                var key = UUID.randomUUID().toString();
                var userId = UUID.randomUUID().toString();
                var orderId = UUID.randomUUID().toString();
                var value = new BigDecimal(Math.random() * 5000 + 1);

                var order = new Order(userId, orderId, value);
                orderDispatcher.send(KafkaService.topic_ecommerce_new_order, key, order);

                var email = "Thank you for your order! We are processing it.";
                emailDispatcher.send(KafkaService.topic_ecommerce_send_email, key, email);
            }
        }
    }
}
