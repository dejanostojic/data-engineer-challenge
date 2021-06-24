package com.tamediadigital.data.engineer.adapter.in;

import com.tamediadigital.data.engineer.core.SimpleClickStreamService;
import com.tamediadigital.data.engineer.core.Visit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class SimpleKafkaListener {

  Logger LOG = LoggerFactory.getLogger(SimpleKafkaListener.class);

  private final SimpleClickStreamService simpleService;

  public SimpleKafkaListener(SimpleClickStreamService simpleService) {
    this.simpleService = simpleService;
  }

  @KafkaListener(topics = "${simple.topic.name}")
  void listener(Visit message) {
    simpleService.handleEvent(message);
  }


}
