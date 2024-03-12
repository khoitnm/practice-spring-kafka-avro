package org.tnmk.pro01simple.sample.person.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String topic = TopicConstants.TOPIC_01;

    public void send(String messageBody){
        logger.info("[KAFKA PUBLISHER] sending data='{}' to topic='{}'", messageBody, topic);
        String messageKey = "sample"+System.nanoTime();
        CompletableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, messageKey, messageBody);
        try {
            SendResult<String, String> sendResult = listenableFuture.get();
            logger.info("[KAFKA PUBLISHER] send result: {}", sendResult.toString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("[KAFKA PUBLISHER] Error: "+e.getMessage(), e);
        }
    }
}