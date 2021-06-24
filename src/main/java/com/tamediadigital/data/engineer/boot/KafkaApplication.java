package com.tamediadigital.data.engineer.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaApplication {

  public static void main(String[] args) throws Exception {

    ConfigurableApplicationContext context = SpringApplication.run(
        KafkaApplication.class, args);
  }

}
