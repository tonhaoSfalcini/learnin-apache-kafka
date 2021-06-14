package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {
        var logService = new LogService();

        var kafkaService = new KafkaService(LogService.class.getSimpleName(), KafkaService.all_topics_ecommerce, logService::parse);
        kafkaService.run();

    }

    private void parse(ConsumerRecord<String, String> record){
        System.out.println("Log Event"+ record.topic() + " >> timestamp "+record.timestamp());
    }
}
