package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PointServiceTest {
  @Mock
  private UserPointTable userPointTable; // Mock 객체 생성

  @InjectMocks
  private PointService pointService; // pointService에 userPointTable Mock 객체 주입

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this); // Mock 객체 초기화 및 주입
  }

  @Test
  void testGetPoint_ExistingUser() {
    when(userPointTable.selectById(1L)).thenReturn(new UserPoint(1L, 1000, System.currentTimeMillis()));

    UserPoint userPoint = pointService.getPoint(1L);
    assertEquals(1000, userPoint.point());
  }

  @Test
  void testGetPoint_NonExistingUser() {
    long nonExistingUserId = 999L; // 존재하지 않는 사용자 ID
    when(userPointTable.selectById(nonExistingUserId)).thenReturn(UserPoint.empty(nonExistingUserId)); // 빈 UserPoint 반환

    UserPoint userPoint = pointService.getPoint(nonExistingUserId);
    assertEquals(nonExistingUserId, userPoint.id()); // ID는 일치해야 함
    assertEquals(0, userPoint.point()); // 포인트는 0이어야 함
  }

}