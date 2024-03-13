In this sample, we only send string event, not Avro object event.

# Note 1 (2024/03)

When using `io.confluent:kafka-avro-serializer`, you have to use Confluent Schema Registry.
Similarly, when using `io.apicurio:apicurio-registry-serdes-avro-serde`, you also have to use ApiCurio Schema Registry.

The reason we are using ApiCurio instead of Confluent is because Confluent is not totally free with some important
features.

# Note 2

When publishing, Spring-Kafka generate the id automatically.
But when receiving, the consumer cannot receive that id value. That's why we have to use a customized
header `customMessageId`



