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

  // TODO(loso): 메서드 이름이 조금 아쉽다. charge() -> chargePoint() 이게 조금더 가독성이 있지 않을까?
  public synchronized UserPoint charge(long userId, long chargePointAmount) {
    if(chargePointAmount <= 0) throw new IllegalArgumentException();

    UserPoint originPoint = userPointTable.selectById(userId);
    return userPointTable.insertOrUpdate(userId, originPoint.point() + chargePointAmount);
  }

  // TODO(loso): synchronized 에 대해 공부하기.
  // 현재: 한 번에 하나의 스레드만 이 메서드를 실행할 수 있도록 보장.

  public synchronized UserPoint getPoint(long userId) {
    return userPointTable.selectById(userId);
  }


  public synchronized UserPoint use(long userId, long point) {
    if(point <= 0) throw new IllegalArgumentException();

    UserPoint originPoint = userPointTable.selectById(userId);

    if (point > originPoint.point()) throw new InsufficientBalanceException();
    return userPointTable.insertOrUpdate(userId, originPoint.point() - point);
  }
}
