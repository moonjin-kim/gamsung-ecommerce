package com.loopers.domain.point;

import com.loopers.domain.user.User;
import com.loopers.domain.user.UserRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public Point chargePoint(Long userId, int amount) {
        User user = userRepository.find(userId).orElseThrow(() ->
                new CoreException(ErrorType.NOT_FOUND, "[id = " + userId + "] 존재하지 않는 회원입니다.")
        );

        Optional<Point> lastPoint = pointRepository.findLastByUser(user);
        int currentBalance = lastPoint.map(Point::getBalance).orElse(0);

        return Point.charge(user, amount, currentBalance);
    }

    public int getBalance(Long userId) {
        return 0;
    }
}
