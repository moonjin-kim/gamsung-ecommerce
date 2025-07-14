package com.loopers.application.point;

import com.loopers.domain.point.Point;
import com.loopers.domain.user.User;

public record PointInfo(Long userId, int balance) {
    public static PointInfo from(User user, Point point) {
        return new PointInfo(
                user.getId(),
                point.getBalance()
        );
    }
}
