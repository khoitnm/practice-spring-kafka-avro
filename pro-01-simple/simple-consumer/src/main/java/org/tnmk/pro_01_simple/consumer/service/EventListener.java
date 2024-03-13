package org.tnmk.pro_01_simple.consumer.service;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventListener {

    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);
    private static final String CUSTOM_MESSAGE_ID = "customMessageId";

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    private static final String topic = TopicConstants.TOPIC_01;

    @PostConstruct
    public void initializeConsumer() {
        resetPartitionOffset("pro01simple_topic01", topic, 0, 2);
    }

    public void resetPartitionOffset(String consumerGroup, String topic, int partition, long offset) {
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        try (Consumer<String, String> consumer = consumerFactory.createConsumer(consumerGroup, null)) {
            consumer.assign(List.of(topicPartition));
            consumer.seek(topicPartition, offset);
            logger.error("Reset offset successfully: topic {}, partition: {}, offset: {}", topic, partition, offset);
        } catch (RuntimeException e) {
            logger.error("Cannot reset offset: topic {}, partition: {}, offset: {}", topic, partition, offset, e);
        }
    }

    @KafkaListener(groupId = "pro01simple_topic01", topics = topic)
    public void receive(@Payload String message, @Headers MessageHeaders headers) {
        logReceiveData(message, headers);
    }

    private void logReceiveData(String message, MessageHeaders headers) {
        Long offset = (Long) headers.get(KafkaHeaders.OFFSET);
        String correlationId = (String) headers.get(KafkaHeaders.CORRELATION_ID);
        String key = (String) headers.get(KafkaHeaders.RECEIVED_KEY);
        String messageId = (String) headers.get(MessageHeaders.ID);
        String customMessageId = (String) headers.get(CUSTOM_MESSAGE_ID);
        logger.info("""
                [KAFKA LISTENER] '{}',
                    offset: {},
                    id: {},
                    customMessageId: {}
                    key: {},
                    correlationId: {},
                    ----------------------------
                {}
                """,
            message,
            offset,
            messageId,
            customMessageId,
            correlationId,
            key,
            formatAllHeaders(headers));
    }

    private String formatAllHeaders(MessageHeaders headers) {
        StringBuilder sb = new StringBuilder();
        for (String key : headers.keySet()) {
            sb.append("\t").append(key).append(": ").append(headers.get(key)).append("\n");
        }
        return sb.toString();
    }
}