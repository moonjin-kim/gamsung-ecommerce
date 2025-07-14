package com.loopers.application.point;

import com.loopers.domain.point.Point;
import com.loopers.domain.point.PointChargeRequest;
import com.loopers.domain.point.PointService;
import com.loopers.domain.user.User;
import com.loopers.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PointFacade {
    private final PointService pointService;
    private final UserService userService;

    public PointInfo chargePoint(Long userId, PointChargeRequest chargeRequest) {
        User user = userService.getUser(userId);

        Point point = pointService.chargePoint(user, chargeRequest.amount());

        return PointInfo.from(user, point);
    }

    public PointInfo getBalance(Long userId) {
        User user = userService.getUser(userId);

        int balance = pointService.getBalance(user);

        return new PointInfo(user.getId(), balance);
    }
}
