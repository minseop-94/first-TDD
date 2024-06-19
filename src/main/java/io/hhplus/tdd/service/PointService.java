package io.hhplus.tdd.service;

import io.hhplus.tdd.point.UserPoint;

public class PointService {

  public UserPoint getPoint(long userId) {
    return new UserPoint(1, 100, 1000);
  }
}
