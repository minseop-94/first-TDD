package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.exception.InsufficientBalanceException;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

@Service
public class PointService {
  // TODO(loso): Domain <-> Entity, mapping 로직 추가하기.
  private final UserPointTable userPointTable;

  public PointService(UserPointTable userPointTable) {
    this.userPointTable = userPointTable;
  }

  public UserPoint charge(long userId, long chargePointAmount) {
    if(chargePointAmount <= 0) throw new IllegalArgumentException();

    UserPoint originPoint = userPointTable.selectById(userId);
    return userPointTable.insertOrUpdate(userId, originPoint.point() + chargePointAmount);
  }

  public UserPoint getPoint(long userId) {
    return userPointTable.selectById(userId);
  }


  public UserPoint use(long userId, long point) {
    if(point <= 0) throw new IllegalArgumentException();

    UserPoint originPoint = userPointTable.selectById(userId);

    if (point > originPoint.point()) throw new InsufficientBalanceException();
    return userPointTable.insertOrUpdate(userId, originPoint.point() - point);
  }
}
