package com.tamediadigital.data.engineer.core;

import java.util.ArrayList;
import java.util.List;

public class DistinctUsersRepositoryTestImpl implements DistinctUsersRepository {

  private List<VisitsPerTimeInterval> data = new ArrayList<>();

  @Override
  public void sendReport(VisitsPerTimeInterval visits) {
    data.add(visits);
  }

  public List<VisitsPerTimeInterval> getData() {
    return data;
  }

  public void setData(List<VisitsPerTimeInterval> data) {
    this.data = data;
  }
}
