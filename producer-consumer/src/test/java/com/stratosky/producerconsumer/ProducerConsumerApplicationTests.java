package com.stratosky.producerconsumer;

import com.stratosky.producerconsumer.consumer.ConsumerAtMostOnce;
import com.stratosky.producerconsumer.producer.ProducerAtLeastOnce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
public class ProducerConsumerApplicationTests {

  @Container
  private static final KafkaContainer kafkaContainer =
          new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

  @DynamicPropertySource
  private static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
  }

  @Autowired
  private ProducerAtLeastOnce producer;
  @Autowired
  private ConsumerAtMostOnce consumer;

  @Test
  public void testProduceAndConsumeMessage() throws InterruptedException {
    String message = "Hi!!!";
    producer.send(message);

    Thread.sleep(5000);
    assertEquals(message, consumer.messages.get(0));

    producer.close();
  }
}
