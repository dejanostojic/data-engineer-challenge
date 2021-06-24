package com.tamediadigital.data.engineer.boot.config;

import com.tamediadigital.data.engineer.adapter.in.SimpleKafkaListener;
import com.tamediadigital.data.engineer.adapter.out.KafkaTopicDistinctUsersRepositoryImpl;
import com.tamediadigital.data.engineer.core.DistinctUsersRepository;
import com.tamediadigital.data.engineer.core.SimpleClickStreamService;
import com.tamediadigital.data.engineer.core.VisitsPerTimeInterval;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class ClickstreamKafkaServiceConfig {

  @Bean
  DistinctUsersRepository outRepo(KafkaTemplate<String, VisitsPerTimeInterval> kafkaTemplate){
    return new KafkaTopicDistinctUsersRepositoryImpl(kafkaTemplate);
  }

  @Bean
  SimpleClickStreamService service(DistinctUsersRepository outRepo){
    return new SimpleClickStreamService(outRepo);
  }

  @Bean
  SimpleKafkaListener listener(SimpleClickStreamService service){
    return new SimpleKafkaListener(service);
  }

}
