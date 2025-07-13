package com.loopers.domain.point;

import com.loopers.domain.user.User;
import com.loopers.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public Point chargePoint(Long userId, int amount) {
        User user = userRepository.find(userId).orElseThrow();

        Point point = Point.charge(user, amount, 0);

        return point;
    }
}
