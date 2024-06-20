package io.hhplus.tdd.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;

import java.util.List;

public class PointHistoryService {
  private PointHistoryTable pointHistoryTable;

  public PointHistoryService(PointHistoryTable pointHistoryTable) {
    this.pointHistoryTable = pointHistoryTable;
  }


  public List<PointHistory> getPointHistories(long id) {
    if (id < 0) throw new IllegalArgumentException();

    return pointHistoryTable.selectAllByUserId(id);
  }

}
