package com.tamediadigital.data.engineerstreaming;


import com.baeldung.spring.kafka.Visit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisitTimestampExtractor implements TimestampExtractor {

  private static final Logger logger = LoggerFactory.getLogger(TimestampExtractor.class);

  public VisitTimestampExtractor() {
  }

  @Override
  public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
//    System.out.println("extracting: " + record);

    Visit visit = (Visit) record.value();
//    System.out.println("extracting: " + value);
//    logger.info("extracting: " + value);

    return visit.getTs() * 1000;
  }
}
