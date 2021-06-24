package com.tamediadigital.data.engineerstreaming;


import com.baeldung.spring.kafka.Visit;

import org.apache.kafka.common.serialization.Serdes.WrapperSerde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.SlidingWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@SpringBootApplication
@EnableKafkaStreams
public class SpringbootStreamsConsumerApplication {

//  @Value("${io.confluent.developer.config.topic.name}")
  private String topicName = "visits-50-partitions";
//  private String topicName = "my-topic1";

  public static void main(final String[] args) {
    SpringApplication.run(SpringbootStreamsConsumerApplication.class, args);
  }

  @Bean
  KStream<String, String> countAgg(final StreamsBuilder builder) {

    String parsedTopic = "parsed-topic-with-object";
////
//    final KStream<Long, Visit> rawStream = builder.stream(topicName,
//        Consumed.with(Serdes.Long(), new PageViewSerde())) ;
////
//    rawStream.map((k,v) -> KeyValue.pair(toMinuteEpoch(v.getTs()), v)).to(parsedTopic);
//

    final KStream<Long, Visit> parsedStram = builder.stream(parsedTopic,
        Consumed.with(new VisitTimestampExtractor())) ;


    Duration oneMinute = Duration.ofMinutes(1);
    Duration fiveSeconds =    Duration.ofSeconds(5);
//    TimeWindows oneMinuteAdvancedByFiveSeconds = TimeWindows.of(oneMinute).advanceBy(fiveSeconds);
    SlidingWindows oneMinuteSlidingWithFiveSecondsGrace = SlidingWindows
        .withTimeDifferenceAndGrace(oneMinute, fiveSeconds);

    parsedStram
//        .map((key, value) -> new KeyValue<>(key, value))
        .groupByKey()
        //.groupByKey(Serialized.with(Serdes.Long(), Serdes.serdeFrom(Visit.class)))
        .windowedBy(oneMinuteSlidingWithFiveSecondsGrace)
    .count() //Materialized.as("count-metric")
//        .suppress(Suppressed.untilWindowCloses(BufferConfig.unbounded()))

        .toStream().foreach( (k,v) -> System.out.println(k.key()+ "->" + v));

    // do windowing here
/*
    final KStream<Long, String> countAgg = stream
        .map((key, value) -> new KeyValue<>(value.getTs(), value.getUid()))
        .groupByKey(with(Serdes.Long(), Serdes.String()))
        //
//        .reduce(Long::sum).toStream();

    countAgg.print(Printed.<String, Long>toSysOut().withLabel("Running count"));
    return countAgg;
    */

return null;
  }


  public long toMinuteEpoch(long ts){
    return Instant.ofEpochSecond( ts )
        .atZone(ZoneOffset.UTC)
        .withSecond(0)
        .withNano(0).toEpochSecond();
  }

  static final class PageViewSerde extends WrapperSerde<Visit> {
    public PageViewSerde() {
      super( new JsonSerializer<>(), new JsonDeserializer<>(Visit.class));
    }
  }

//  static final class VisitSerde extends

}
