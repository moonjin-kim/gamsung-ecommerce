package com.loopers.domain.point;

import com.loopers.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class PointService {
    private final PointRepository pointRepository;

    public Point chargePoint(User user, int amount) {

        Optional<Point> lastPoint = pointRepository.findLastByUser(user);
        int currentBalance = lastPoint.map(Point::getBalance).orElse(0);

        return Point.charge(user, amount, currentBalance);
    }

    public int getBalance(User user) {
        Optional<Point> lastPoint = pointRepository.findLastByUser(user);
        return lastPoint.map(Point::getBalance).orElse(0);
    }
}
