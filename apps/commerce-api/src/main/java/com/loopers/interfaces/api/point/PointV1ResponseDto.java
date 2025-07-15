package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointBalanceInfo;

public class PointV1ResponseDto {
    public record PointBalanceResponse(
            String account,
            int balance
    ){
        public static PointV1ResponseDto.PointBalanceResponse from(PointBalanceInfo pointBalanceInfo) {
            return new PointV1ResponseDto.PointBalanceResponse(
                    pointBalanceInfo.account(),
                    pointBalanceInfo.balance()
            );
        }
    }
}
