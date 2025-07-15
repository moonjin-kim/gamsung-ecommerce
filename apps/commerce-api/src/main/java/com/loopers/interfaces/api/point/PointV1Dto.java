package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointInfo;

public class PointV1Dto {
    public record PointBalanceResponse(
            int balance
    ){
        public static PointV1Dto.PointBalanceResponse from(PointInfo pointInfo) {
            return new PointV1Dto.PointBalanceResponse(
                    pointInfo.balance()
            );
        }
    }
}
