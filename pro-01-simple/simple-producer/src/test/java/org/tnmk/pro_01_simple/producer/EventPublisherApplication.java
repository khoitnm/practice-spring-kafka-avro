package org.tnmk.pro_01_simple.producer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tnmk.pro_01_simple.producer.service.EventProducer;

@SpringBootTest
@Disabled("This file is used for manual run, not for automation test.")
class EventPublisherApplication {

    @Autowired
    private EventProducer eventProducer;

    @Test
    void publishEvent() {
        eventProducer.send("ABC_" + System.nanoTime());
    }

}