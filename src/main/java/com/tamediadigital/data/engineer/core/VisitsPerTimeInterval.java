package com.tamediadigital.data.engineer.core;

public class VisitsPerTimeInterval {

  private TimeUnit timeUnit;
  private long intervalStart;
  private int distinctUsersCount;

  public VisitsPerTimeInterval() {
  }

  public VisitsPerTimeInterval(TimeUnit timeUnit, long intervalStart, int distinctUsersCount) {
    this.timeUnit = timeUnit;
    this.intervalStart = intervalStart;
    this.distinctUsersCount = distinctUsersCount;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

  public long getIntervalStart() {
    return intervalStart;
  }

  public void setIntervalStart(long intervalStart) {
    this.intervalStart = intervalStart;
  }

  public int getDistinctUsersCount() {
    return distinctUsersCount;
  }

  public void setDistinctUsersCount(int distinctUsersCount) {
    this.distinctUsersCount = distinctUsersCount;
  }
}
