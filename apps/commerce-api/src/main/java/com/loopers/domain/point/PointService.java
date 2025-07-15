package com.loopers.domain.point;

import com.loopers.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PointService {
    private final PointRepository pointRepository;

    public Point chargePoint(User user, int amount) {
        Optional<Point> lastPoint = pointRepository.findLastByUser(user);
        int currentBalance = lastPoint.map(Point::getBalance).orElse(0);

        return Point.charge(user, amount, currentBalance);
    }

    public Optional<Point> getBalance(User user) {
        return pointRepository.findLastByUser(user);
    }
}
