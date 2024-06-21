package io.hhplus.tdd.integration;

import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Integration Test -> 의존성이 필요
@SpringBootTest
public class ConcurrencyTest {
  @Autowired
  private PointService pointService;

  @Test
  void concurrency_test() throws ExecutionException, InterruptedException {
    // given
    pointService.charge(1, 100000);

    // when
    CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> pointService.use(1, 10000));
    CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> pointService.charge(1, 4000));
    CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> pointService.use(1, 100));

    CompletableFuture.allOf(future1, future2, future3).join();

    // then
    UserPoint userPoint = pointService.getPoint(1);
    assertEquals(userPoint.point(), 100000 - 10000 + 4000 - 100);
  }


}
