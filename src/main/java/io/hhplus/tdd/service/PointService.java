package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

@Service
public class PointService {
  private final UserPointTable userPointTable;

  public PointService(UserPointTable userPointTable) {
    this.userPointTable = userPointTable;
  }

  public UserPoint getPoint(long userId) {
    return userPointTable.selectById(userId);
  }
}
