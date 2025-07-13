package com.loopers.application.point;

import com.loopers.application.user.UserInfo;
import com.loopers.domain.point.Point;
import com.loopers.domain.user.User;

public record PointInfo(int balance) {
    public static PointInfo from(Point point) {
        return new PointInfo(
                point.getBalance()
        );
    }
}
