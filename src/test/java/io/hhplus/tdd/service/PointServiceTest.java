package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

  // TODO(loso): 예외를 더 세분화 할 것.
  // 비지니스 로직
  // (Case) 존재하지 않는 사용자 ID로 포인트를 충전할 때: 예외가 발생하거나 기본값(0)이 반환되는지 확인
  // (Case) 0, -500과 같이 음수 충전 포인트 금액으로 포인트를 충전할 때: IllegalArgumentException 발생
  // (Case) 포인트 잔액과 충전 포인트 총액이 올바르게 증가하는지 확인

  // (?) 충전 내역(PointHistory)이 올바르게 기록되는지 확인

//  @Test
//  public void charge_nonExistentUser_throwsException() {
//    // given
//    long nonExistentUserId = 999L;
//    long chargePointAmount = 1000L;
//
//    // when & then
//    assertThrows(
//      RuntimeException.class,
//      () -> pointService.charge(nonExistentUserId, chargePointAmount)
//    );
//  }

  @Test
  public void charge_negativeAmount_throwsIllegalArgumentException() {
    // given
    long userId = 1L;
    long negativeAmount = -500L;

    // when & then
    assertThrows(IllegalArgumentException.class, () -> pointService.charge(userId, negativeAmount));
  }

  // TODO(loso): 이게 테스트로서 의미가 있나? 더한 값들이 return되게 결정되어 있는데? 
  @Test
  public void charge_positiveAmount_increasesBalanceAndTotal() {
    // given
    long userId = 1L;
    long chargePointAmount = 500L;
    UserPoint initialUserPoint = new UserPoint(userId, 1000L, 1500L); // 초기 값 설정

    // when
    when(userPointTable.selectById(userId)).thenReturn(initialUserPoint);

    // Corrected mock behavior for insertOrUpdate
    UserPoint updatedUserPoint = new UserPoint(userId, initialUserPoint.point() + chargePointAmount, 0);
    when(userPointTable.insertOrUpdate(userId, initialUserPoint.point() + chargePointAmount))
        .thenReturn(updatedUserPoint);

    UserPoint result = pointService.charge(userId, chargePointAmount);

    // then
    assertEquals(updatedUserPoint, result);
  }


}