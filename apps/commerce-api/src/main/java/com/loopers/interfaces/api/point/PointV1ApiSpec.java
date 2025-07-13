package com.loopers.interfaces.api.point;

import com.loopers.domain.point.PointChargeRequest;
import com.loopers.interfaces.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;

public interface PointV1ApiSpec {
    @Operation(
            summary = "포인트 충전",
            description = "현재 유저에 amont만큼의 포인트를 추가한다."
    )
    ApiResponse<PointV1Dto.PointBalanceResponse> register(Long userId, PointChargeRequest chargeRequest);
}
