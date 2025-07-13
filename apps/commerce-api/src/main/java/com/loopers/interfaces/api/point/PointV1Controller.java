package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointFacade;
import com.loopers.application.point.PointInfo;
import com.loopers.application.user.UserFacade;
import com.loopers.application.user.UserInfo;
import com.loopers.domain.point.PointChargeRequest;
import com.loopers.domain.user.UserRegisterRequest;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.user.UserV1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/points")
public class PointV1Controller implements PointV1ApiSpec{
    private final PointFacade pointFacade;

    @PostMapping("/charge")
    @Override
    public ApiResponse<PointV1Dto.PointBalanceResponse> register(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PointChargeRequest body
    ) {
        PointInfo info = pointFacade.chargePoint(userId, body);
        PointV1Dto.PointBalanceResponse response = PointV1Dto.PointBalanceResponse.from(info);
        return ApiResponse.success(response);
    }
}
