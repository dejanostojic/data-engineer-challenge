package com.tamediadigital.data.engineer.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleClickStreamServiceTest {

  DistinctUsersRepositoryTestImpl outRepo;

  @Test
  void handleEvent() {
    // given
    SimpleClickStreamService service = new SimpleClickStreamService(outRepo);
    // when
    service.handleEvent(new Visit(100, "u1"));
    service.handleEvent(new Visit(101, "u2"));
    service.handleEvent(new Visit(102, "u1"));
    service.handleEvent(new Visit(126, "u3"));

    // then
    assertEquals(1, outRepo.getData().size());
    VisitsPerTimeInterval visit = outRepo.getData().get(0);
    assertEquals(TimeUnit.Minute, visit.getTimeUnit());
    assertEquals(60L, visit.getIntervalStart());
    assertEquals(2, visit.getDistinctUsersCount());

  }

  @BeforeEach
  public void beforeEach(){
    outRepo = new DistinctUsersRepositoryTestImpl();
  }



}