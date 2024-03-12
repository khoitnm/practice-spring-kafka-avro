package org.tnmk.pro01simple.sample.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.pro01simple.sample.person.producer.EventProducer;

@Service
public class Initiation {

    @Autowired
    private EventProducer producer;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
//        Person person = Person.newBuilder()
//            .setNickName("PersonV1_"+System.nanoTime())
//            .setRealName("RealName_"+System.nanoTime())
//            .build();
        String message = "Message_01_" + System.nanoTime();
        producer.send(message);
    }
}
