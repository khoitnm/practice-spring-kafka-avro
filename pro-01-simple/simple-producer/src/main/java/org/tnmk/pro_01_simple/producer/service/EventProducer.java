package org.tnmk.pro_01_simple.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EventProducer {
    private static final String CUSTOM_MESSAGE_ID = "customMessageId";
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String messageBody) {
        // logger.info("[KAFKA PUBLISHER] sending data='{}' to topic='{}'", messageBody, topic);
        String topic = TopicConstants.TOPIC_01;
        String customMessageId = UUID.randomUUID().toString();
        String key = "entityId_" + System.nanoTime();
        String correlationId = java.util.UUID.randomUUID().toString();
        MessageHeaders messageHeaders = new MessageHeaders(Map.of(
            KafkaHeaders.TOPIC, topic,
            KafkaHeaders.CORRELATION_ID, correlationId,
            KafkaHeaders.KEY, key,
            CUSTOM_MESSAGE_ID, customMessageId
        ));
        // The ID is generated automatically by MessageHeaders.
        // However, when Consumer receive the message, it cannot get this Id value!!!
        UUID id = messageHeaders.getId();
        Message<String> message = MessageBuilder.createMessage(messageBody, messageHeaders);
        CompletableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(message);
        try {
            SendResult<String, String> sendResult = listenableFuture.get();
            logger.info("""
                    [KAFKA PUBLISHER] sent successfully: {}
                        id: {},
                        key: {},
                        correlationId: {},
                        ----------------------------
                    {}
                    """,
                messageBody,
                id,
                key,
                correlationId,
                formatAllHeaders(messageHeaders));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("[KAFKA PUBLISHER] Error: " + e.getMessage(), e);
        }
    }

    private String formatAllHeaders(MessageHeaders headers) {
        StringBuilder sb = new StringBuilder();
        for (String key : headers.keySet()) {
            sb.append("\t").append(key).append(": ").append(headers.get(key)).append("\n");
        }
        return sb.toString();
    }
}