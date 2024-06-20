package io.hhplus.tdd.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PointHistoryServiceTest {

  private final PointHistoryTable pointHistoryTable = Mockito.mock(PointHistoryTable.class);
  private final PointHistoryService pointHistoryService = new PointHistoryService(pointHistoryTable);
  private List<PointHistory> mockPointHistories;

    @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this); // Mock 객체 초기화 및 주입

    mockPointHistories = List.of(
      new PointHistory(1L, 1L, 100, TransactionType.CHARGE, 0),
      new PointHistory(2L, 1L, -50, TransactionType.USE, 0)
    );
  }

  @Test
  void testGetPointHistoriesSuccess() {
    //given
    when(pointHistoryTable.selectAllByUserId(1L)).thenReturn(mockPointHistories);

    // when
    List<PointHistory> histories = pointHistoryService.getPointHistories(1L);

    // then
    assertEquals(2, histories.size());
    assertEquals(100, histories.get(0).amount());
    assertEquals(-50, histories.get(1).amount());

  }

  @Test
  void testGetPointHistoriesWithNonExistingUserId() {
    // given
    when(pointHistoryTable.selectAllByUserId(100L)).thenReturn(Collections.emptyList());

    // when
    List<PointHistory> histories = pointHistoryService.getPointHistories(100L);

    // then
    assertTrue(histories.isEmpty());
  }

//  @Test
//  void testGetPointHistoriesWithNullUserId() {
//    assertThrows(IllegalArgumentException.class, () -> pointHistoryService.getPointHistories(null));
//  }

  @Test
  void testGetPointHistoriesWithNegativeUserId() {
    assertThrows(IllegalArgumentException.class, () -> pointHistoryService.getPointHistories(-1L));
  }





}