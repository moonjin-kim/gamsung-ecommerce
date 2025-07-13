package com.loopers.application.point;

import com.loopers.domain.point.Point;
import com.loopers.domain.point.PointChargeRequest;
import com.loopers.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PointFacade {
    private final PointService pointService;

    public PointInfo chargePoint(Long userId, PointChargeRequest chargeRequest) {
        Point point = pointService.chargePoint(userId, chargeRequest.amount());

        return PointInfo.from(point);
    }
}
