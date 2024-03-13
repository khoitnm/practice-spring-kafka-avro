package org.tnmk.pro_01_simple.producer.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    // We don't need to create topic when starting the app anymore.
    // The topic is already created in docker-compose.yml file already.
//    @Bean
//    public NewTopic topic01() {
//        return TopicBuilder.name(TopicConstants.TOPIC_01).partitions(1).replicas(1).build();
//    }
}
