package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.CustomException;
import io.hhplus.tdd.service.PointHistoryService;
import io.hhplus.tdd.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {
  private final PointService pointService;
  private PointHistoryService pointHistoryService;
  private static final Logger log = LoggerFactory.getLogger(PointController.class);

  @Autowired // Spring이 해당 생성자를 통해 PointService를 주입하도록 설정
  public PointController(PointService pointService, PointHistoryService pointHistoryService) {
    this.pointService = pointService;
    this.pointHistoryService = pointHistoryService;
  }

  /**
   * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
   */
    @GetMapping("{id}")
    public ResponseEntity<UserPoint> point(
        @PathVariable(name = "id") Long id
    ) {
      if (String.valueOf(id).isBlank() || !String.valueOf(id).matches("\\d+")) {
        throw new CustomException("400", "Invalid user ID");
//        throw new IllegalArgumentException("Invalid user ID");
      }
      UserPoint userPoint = pointService.getPoint(id);
      return ResponseEntity.ok(userPoint);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @GetMapping("{id}/histories")
    public List<PointHistory> history(
            @PathVariable(name = "id") Long id
//            @PathVariable long id
    ) {
      if (id == null) {
        throw new IllegalArgumentException("id cannot be null");
      }
      return pointHistoryService.getPointHistories(id);
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPoint charge(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        return new UserPoint(0, 0, 0);
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPoint use(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        return new UserPoint(0, 0, 0);
    }
}
