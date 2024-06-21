package io.hhplus.tdd.database;

import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 해당 Table 클래스는 변경하지 않고 공개된 API 만을 사용해 데이터를 제어합니다.
 */
@Component
public class UserPointTable {

    private final Map<Long, UserPoint> table = new HashMap<>();

    // TODO(loso): Lock의 종류에 대해서, 무엇이 다른지 공부하기.
    private final Lock lock = new ReentrantLock();

    public UserPoint selectById(Long id) {
        // TODO(loso): throttle() 역할은 뭐야?
        //  thread를 잠시 멈춤 하는게 왜 동시성 제어에 도움이 될까?
        //  비동기 코드로 작성한게 아니어서, thread를 멈추지 않으면 잡혀있나?
        throttle(200);
        return table.getOrDefault(id, UserPoint.empty(id));
    }

    public UserPoint insertOrUpdate(long id, long amount) {
        throttle(300);
        UserPoint userPoint = new UserPoint(id, amount, System.currentTimeMillis());
        table.put(id, userPoint);
        return userPoint;
    }

    private void throttle(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * millis));
        } catch (InterruptedException ignored) {

        }
    }
}
