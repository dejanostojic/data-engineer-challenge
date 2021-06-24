package com.tamediadigital.data.engineer.boot.config;

import com.tamediadigital.data.engineer.core.VisitsPerTimeInterval;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaProducerConfig {

  @Value("${simple.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        bootstrapServers);

    return props;
  }

  @Bean
  public ProducerFactory<String, VisitsPerTimeInterval> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(),
        new JsonSerializer<>());
  }

  @Bean
  public KafkaTemplate<String, VisitsPerTimeInterval> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
