package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointInfo;
import com.loopers.application.user.UserInfo;
import com.loopers.domain.user.Sex;
import com.loopers.interfaces.api.user.UserV1Dto;

import java.time.LocalDate;

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
