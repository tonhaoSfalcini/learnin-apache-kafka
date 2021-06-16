package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {

    public static String all_topics_ecommerce = "ecommerce.*";
    public static String topic_ecommerce_new_order = "ecommerce_new_order";
    public static final String topic_ecommerce_send_email = "ecommerce_send_email";
    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;
    private final String groupId;


    public KafkaService(String groupId, String topic, ConsumerFunction parse) {
        this.consumer = new KafkaConsumer<>(properties());
        consumer.subscribe(Collections.singletonList(topic));
        this.parse = parse;
        this.groupId = groupId;
    }

    public KafkaService(String groupId, Pattern topic, ConsumerFunction parse) {
        this.consumer = new KafkaConsumer<>(properties());
        consumer.subscribe(topic);
        this.parse = parse;
        this.groupId = groupId;
    }
    public void run(){
        //noinspection InfiniteLoopStatement
        while (true){
            var records = consumer.poll(Duration.ofMillis(100));
            if(!records.isEmpty()){
                System.out.println("Found " + records.count() + " records.");
                for(var record : records){
                    parse.consume(record);
                }
            }
        }
    }

    private Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, String.class.getSimpleName());
        return properties;
    }


    @Override
    public void close() {
        this.consumer.close();
    }
}
