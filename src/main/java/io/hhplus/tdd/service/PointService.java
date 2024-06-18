package io.hhplus.tdd.service;

import io.hhplus.tdd.point.UserPoint;

public interface PointService {

  UserPoint getPoint(long userId);
}
