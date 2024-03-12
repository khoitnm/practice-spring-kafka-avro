package org.tnmk.pro01simple.sample.person.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.tnmk.pro01simple.sample.person.producer.TopicConstants;

@Configuration
public class KafkaTopicsConfig {
    // We don't need to create topic when starting the app anymore.
    // The topic is already created in docker-compose.yml file already.
//    @Bean
//    public NewTopic topic01() {
//        return TopicBuilder.name(TopicConstants.TOPIC_01).partitions(1).replicas(1).build();
//    }
}
