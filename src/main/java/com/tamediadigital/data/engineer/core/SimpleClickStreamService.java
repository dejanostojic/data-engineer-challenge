package com.tamediadigital.data.engineer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple service that accepts one by event and aggregates results
 * Singe threaded solution only to get POC
 * Most of the time spent on the attempt in solving this in spark streaming, but failed to get it working
 */
public class SimpleClickStreamService {

  private final Logger LOG = LoggerFactory.getLogger(SimpleClickStreamService.class);

  private DistinctUsersRepository outRepo;

  public SimpleClickStreamService(DistinctUsersRepository outRepo) {
    this.outRepo = outRepo;
  }

  private Set<String> visitSet = new HashSet<>();
  private Set<String> visitSetNextMin = new HashSet<>();
  private long currentMinute = 0L;
  private final int MINUTE_SECONDS = 60;
  private final int MAX_LATENCY_SECONDS = 5;

  public void handleEvent(Visit visit) {

    long ts = visit.getTs();
    long minute = toMinuteEpoch(ts);
    if (currentMinute == 0) {
      currentMinute = minute;
    }
    // if same minute just add user to set
    if (minute == currentMinute) {
      visitSet.add(visit.uid);
    } else {

      visitSetNextMin.add(visit.uid);

      if (ts > currentMinute + MINUTE_SECONDS + MAX_LATENCY_SECONDS) {
        // if max latency reached send events
        int visitsCount = visitSet.size();
        LOG.info("minute: " + currentMinute + " total count: " + visitsCount);
        outRepo.sendReport(new VisitsPerTimeInterval(TimeUnit.Minute, currentMinute, visitsCount));
        visitSet.clear();

        // copy from next to current and move minute
        visitSet.addAll(visitSetNextMin);
        visitSetNextMin.clear();
        currentMinute = minute;
      }
    }

  }

  public long toMinuteEpoch(long ts) {
    return Instant.ofEpochSecond(ts)
        .atZone(ZoneOffset.UTC)
        .withSecond(0)
        .withNano(0)
        .toEpochSecond();
  }

}
