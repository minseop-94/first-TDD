package io.hhplus.tdd.service;

import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

@Service
public class PointServiceStub implements PointService {

  @Override
  public UserPoint getPoint(long userId) {
    return new UserPoint(userId, 100, 50);
  }
}
