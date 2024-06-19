package io.hhplus.tdd.point;

import io.hhplus.tdd.service.PointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PointController.class)
class PointControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointService pointService;


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

        // 응답객체가 제대로 돌아오는지 확인. ResponseEntity..
    }




    @Test
    void testGetPoint() throws Exception {
        when(pointService.getPoint(1L)).thenReturn(new UserPoint(1L, 1000, System.currentTimeMillis()));

        mockMvc.perform(get("/point/1"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.point").value(1000));
    }

    // TODO(loso): long 타입의 id값을 유지하면, null값에 대한 예외처리가 되지 않는다. pathVariable로 받아버리는 순간, 에러가 발생해 버린다.
    //  id 값을 String 타입으로 바꾸거나, 아님 test case가 잘 못 작성 되었다.
//    @Test
//    void testPoint_InvalidUserId_Null() throws Exception {
//        mockMvc.perform(get("/point/null")) // null
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("Invalid user ID"));
//    }

    // 위랑 마찬가지.
//    @Test
//    void testPoint_InvalidUserId_NonNumeric() throws Exception {
//        mockMvc.perform(get("/point/abc")) // 숫자가 아닌 문자열
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("Invalid user ID"));
//    }

    @Test
    void testPoint_ExistingUser() throws Exception {
        long existingUserId = 1L;
        UserPoint userPoint = new UserPoint(existingUserId, 1000, System.currentTimeMillis());
        when(pointService.getPoint(existingUserId)).thenReturn(userPoint);

        mockMvc.perform(get("/point/{id}", existingUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingUserId))
                .andExpect(jsonPath("$.point").value(userPoint.point()));
    }

    @Test
    void testPoint_NonExistingUser() throws Exception {
        long nonExistingUserId = 999L;
        when(pointService.getPoint(nonExistingUserId)).thenReturn(UserPoint.empty(nonExistingUserId));

        mockMvc.perform(get("/point/{id}", nonExistingUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(nonExistingUserId))
                .andExpect(jsonPath("$.point").value(0));
    }

    @Test
    public void testPointResponseContentType() throws Exception {
        long id = 1L;

        mockMvc.perform(get("/point/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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