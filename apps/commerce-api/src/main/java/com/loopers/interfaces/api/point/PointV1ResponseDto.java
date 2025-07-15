package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointInfo;

public class PointV1ResponseDto {
    public record PointBalanceResponse(
            int balance
    ){
        public static PointV1ResponseDto.PointBalanceResponse from(PointInfo pointInfo) {
            return new PointV1ResponseDto.PointBalanceResponse(
                    pointInfo.balance()
            );
        }
    }
}
