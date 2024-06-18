package io.hhplus.tdd.point;

import io.hhplus.tdd.service.UserPointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PointController.class)
class PointControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserPointService userpointService;

//  @BeforeEach
//  void setUp() {
//    UserPointTable userPointTable = new UserPointTable();
//    userpointService = new UserPointService(userPointTable); // 실제 구현체 초기화
//  }


  @Test
  void point() {
    // Params - need validation
    String userId;

    // [Success]
    // (Case) 유효한 사용자 ID로 포인트를 조회할 때: 사용자의 포인트 정보를 반환

    // [Fail]
    // userId 유효성 검사
    // (Case) 빈 문자열("") 사용자 ID로 포인트를 조회할 때: IllegalArgumentException 발생
    // (Case) null 사용자 ID로 포인트를 조회할 때: IllegalArgumentException 발생
    // (Case) "abc"와 같이 숫자가 아닌 문자열 사용자 ID로 포인트를 조회할 때: NumberFormatException 발생

    // 비지니스 로직 검사
    // (Case) 존재하는 사용자 ID로 조회했을 때, UserPoint 반환 확인
    // (Case) 존재하지 않는 사용자 ID로 조회했을 때, 기본값이 반환되는지 확인
  }

  @ParameterizedTest
  @ValueSource(strings = {" ", "abc"})
  void invalidUserId_shouldThrowException(String userId) {
    assertThrows(NumberFormatException.class, () -> userpointService.getPoint(Long.parseLong(userId)));
  }

  @ParameterizedTest
  @ValueSource(strings = {""})
  void emptyStringUserId_shouldThrowException(String userId) {
    assertThrows(IllegalArgumentException.class, () -> userpointService.getPoint(Long.parseLong(userId)));
  }

  @Test
  void nullUserId_shouldThrowException() {
//    assertThrows(NullPointerException.class, () -> userpointService.getPoint(null));
    assertThrows(IllegalArgumentException.class, () -> userpointService.getPoint(null));

  }

  @Test
  void validUserId_shouldReturnUserPoint() {
    // 존재하는 유저 ID 예시
    long existingUserId = 1234L;
    UserPoint expectedUserPoint = new UserPoint(existingUserId, 100L, 11234L);

    UserPoint actualUserPoint = userpointService.getPoint(existingUserId);
    assertEquals(expectedUserPoint, actualUserPoint);
  }

  @Test
  void nonExistingUserId_shouldReturnDefaultUserPoint() {
    // 존재하지 않는 유저 ID 예시
    // given
    long nonExistingUserId = 5678L;
    UserPoint expectedDefaultUserPoint = UserPoint.empty(nonExistingUserId);

    // when
    UserPoint actualUserPoint = userpointService.getPoint(nonExistingUserId);

    // then
    // updateMillis까지 비교하려니, System.currentTimeMillis() 때문에 계속 다르게 됨.
//    assertEquals(expectedDefaultUserPoint, actualUserPoint);
    assertEquals(expectedDefaultUserPoint.id(), actualUserPoint.id());
    assertEquals(expectedDefaultUserPoint.point(), actualUserPoint.point());
  }



  @Test
  void history() {
    // Params - need validation
    String userId;

    // [Success]
    // (Case) 유효한 사용자 ID로 포인트 충전/이용 내역을 조회를 조회할 때: 유저의 포인트 충전/이용 내역을 반환

    // [Fail]
    // userId 유효성 검사
    // (Case) 빈 문자열("") 사용자 ID로 포인트를 조회할 때: IllegalArgumentException 발생
    // (Case) null 사용자 ID로 포인트를 조회할 때: IllegalArgumentException 발생
    // (Case) "abc"와 같이 숫자가 아닌 문자열 사용자 ID로 포인트를 조회할 때: NumberFormatException 발생

    // 비지니스 로직
    // (Case) 존재하는 사용자 ID로 조회했을 때, 올바른 List.of(PointHistory) 가 반환되는지 확인
    // (Case) 존재하지 않는 사용자 ID로 조회했을 때, 빈 List 가 반환되는지 확인
    // (Case) 내역이 있는 사용자 ID로 조회했을 때, 최신 내역부터 순서대로 반환되는지 확인
  }

  @Test
  void charge() {
    // Params - need validation
    long userId;
    long chargePointAmount;

    // [Success]
    // (Case) 유효한 userId, 유효한 pointAmount 로 포인트를 충전할 때: 특정 유저의 포인트를 충전

    // [Fail]
    // chargePointAmount 유효성 검사
    // (Case) -1과 같이 음수 사용자 ID로 포인트를 충전할 때: IllegalArgumentException 발생
    // (Case) 0 충전 포인트 금액으로 포인트를 충전할 때: IllegalArgumentException 발생
    // (Case) -500과 같이 음수 충전 포인트 금액으로 포인트를 충전할 때: IllegalArgumentException 발생

    // 비지니스 로직
    // (Case) 존재하는 사용자 ID로 조회했을 때, 올바른 UserPoint 반환 확인
    // (Case) 포인트 잔액과 충전 포인트 총액이 올바르게 증가하는지 확인
    // (Case) 충전 내역(PointHistory)이 올바르게 기록되는지 확인
    // (Case) 존재하지 않는 사용자 ID로 충전했을 때, 예외가 발생하거나 기본값(0)이 반환되는지 확인
  }

  @Test
  void use() {
    // Params - need validation
    long userId;
    long costPointAmount;

    // [Success]
    // (Case) 유효한 userId, 유효한 costPointAmount 로 포인트를 사용할 때: 특정 유저의 포인트를 차감


    // [Fail]
    // costPointAmount 유효성 검사
    // (Case) -1과 같이 음수 사용자 ID로 포인트를 충전할 때: IllegalArgumentException 발생
    // (Case) 0 충전 포인트 금액으로 포인트를 충전할 때: IllegalArgumentException 발생
    // (Case) -500과 같이 음수 충전 포인트 금액으로 포인트를 충전할 때: IllegalArgumentException 발생

    // (Case) 사용자의 사용 가능 포인트가 사용 금액만큼 감소하는지 확인한다.
    // (Case) 사용 내역이 데이터베이스 또는 저장소에 정확하게 기록되는지 확인한다.
    // (Case) 성공 응답과 함께 사용 후 포인트 정보를 반환하는지 확인한다.
    // (Case) 사용 가능 포인트가 부족할 경우 예외가 발생하거나 에러 응답을 반환하는지 확인한다.
  }
}