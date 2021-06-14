package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static String all_topics_ecommerce = "ecommerce.*";
    public static String topic_ecommerce_new_order = "ecommerce_new_order";
    public static String topic_ecommerce_send_email = "ecommerce_send_email";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());

        var value = UUID.randomUUID().toString() +",Smartphone,1280.53";
        var record = new ProducerRecord<String, String>(topic_ecommerce_new_order, value, value);

        var email = "Thank you for your order! We are processing it.";
        var emailRecord = new ProducerRecord<>(topic_ecommerce_send_email, email, email);

        producer.send(record, getCallback()).get();
        producer.send(emailRecord ,getCallback()).get();
    }

    private static Callback getCallback() {
        return (data, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
            System.out.println(data.topic() + "::partition " + data.partition() + " / offset " + data.offset() + ">> timestamp " + data.timestamp());
        };
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
