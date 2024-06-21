package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.exception.InsufficientBalanceException;
import io.hhplus.tdd.point.UserPoint;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

@Service
@Log4j2
public class PointService {
  // TODO(loso): Domain <-> Entity, mapping 로직 추가하기.
  private final UserPointTable userPointTable;

  public PointService(UserPointTable userPointTable) {
    this.userPointTable = userPointTable;
  }

  // FIXME(loso): 메서드 이름이 조금 아쉽다. charge() -> chargePoint() 이게 조금더 가독성이 있지 않을까?
  public synchronized UserPoint charge(long userId, long chargePointAmount) {
    if(chargePointAmount <= 0) throw new IllegalArgumentException();

    Lock lock = userPointTable.getLock();
    lock.lock();
    UserPoint originPoint = new UserPoint(0, 0, 0);

    try {
          originPoint = userPointTable.selectById(userId);
          long amount = originPoint.point() + chargePointAmount;
          return userPointTable.insertOrUpdate(userId, amount);
    }

    // FIXME(loso): catch로 예외를 처리한 다음에, 기본값을 return 하는게 좋은 코일까요? 현재는 Exception 처리를 globalExceptionHandler에게 맡겨버리는 것 같습니다.

//    catch (Exception e) {
//      log.error("포인트 충전시 에러 발생 : " + e.getStackTrace());
//    }
    finally {
      lock.unlock();
    }

//    return originPoint;
  }

  // TODO(loso): synchronized 에 대해 공부하기.
  // 현재: 한 번에 하나의 스레드만 이 메서드를 실행할 수 있도록 보장.

  public synchronized UserPoint getPoint(long userId) {
    return userPointTable.selectById(userId);
  }


  public synchronized UserPoint use(long userId, long point) {
    if(point <= 0) throw new IllegalArgumentException();

    Lock lock = userPointTable.getLock();
    lock.lock();

    try {
      UserPoint originPoint = userPointTable.selectById(userId);

      if (point > originPoint.point()) throw new InsufficientBalanceException();
      return userPointTable.insertOrUpdate(userId, originPoint.point() - point);

    } finally {
      lock.unlock();
    }
  }
}
