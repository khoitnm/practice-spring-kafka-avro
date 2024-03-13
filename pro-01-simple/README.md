In this sample, we only send string event, not Avro object event.

# Manual Test:

## Start Kafka

Run this file in terminal:
[practice-spring-kafka-avro\kafka-server\start-01.bat](..\kafka-server\start-01.bat)

## Publish event

Trigger this method to publishing an event.
[org.tnmk.pro_01_simple.producer.EventPublisherApplication](.\simple-producer\src\test\java\org\tnmk\pro_01_simple\producer\EventPublisherApplication.java)
.publishEvent()

## Listen to event

Start [Pro01SimpleConsumerApplication.java](\simple-consumer\src\main\java\org\tnmk\pro_01_simple\consumer\Pro01SimpleConsumerApplication.java)

# Note 1 (2024/03)

When using `io.confluent:kafka-avro-serializer`, you have to use Confluent Schema Registry.
Similarly, when using `io.apicurio:apicurio-registry-serdes-avro-serde`, you also have to use ApiCurio Schema Registry.

The reason we are using ApiCurio instead of Confluent is because Confluent is not totally free with some important
features.

# Note 2

When publishing, Spring-Kafka generate the id automatically.
But when receiving, the consumer cannot receive that id value. That's why we have to use a customized
header `customMessageId`



