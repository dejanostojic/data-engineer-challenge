package com.tamediadigital.data.engineer.adapter.out;

import com.tamediadigital.data.engineer.core.DistinctUsersRepository;
import com.tamediadigital.data.engineer.core.VisitsPerTimeInterval;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaTopicDistinctUsersRepositoryImpl implements DistinctUsersRepository {

  KafkaTemplate<String, VisitsPerTimeInterval> kafkaTemplate;

  @Value("${simple.kafka.publish-topic-name}")
  private String publishTopicName;

  public KafkaTopicDistinctUsersRepositoryImpl(
      KafkaTemplate<String, VisitsPerTimeInterval> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }


  @Override
  public void sendReport(VisitsPerTimeInterval visits) {
    kafkaTemplate.send(publishTopicName, visits);
  }
}
