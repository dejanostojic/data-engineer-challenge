package com.tamediadigital.data.engineerstreaming;


import com.baeldung.spring.kafka.Visit;

import org.apache.kafka.common.serialization.Serdes.WrapperSerde;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class PageViewSerde extends WrapperSerde<Visit> {
  public PageViewSerde() {
    super( new JsonSerializer<>(), new JsonDeserializer<>(Visit.class));
  }
}