package org.tnmk.pro_01_simple.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class EventListener {

    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);
    private static final String CUSTOM_MESSAGE_ID = "customMessageId";

    @KafkaListener(groupId = "pro01simple_topic01", topics = TopicConstants.TOPIC_01)
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