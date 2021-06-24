package com.tamediadigital.data.engineer.core;


public class Visit {
  long ts;
  String uid;

  public Visit() {
  }

  public Visit(long ts, String uid) {
    this.ts = ts;
    this.uid = uid;
  }

  public long getTs() {
    return ts;
  }

  public void setTs(long ts) {
    this.ts = ts;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  @Override
  public String toString() {
    return "Visit{" +
        "ts=" + ts +
        ", uid='" + uid + '\'' +
        '}';
  }
}
