package com.loopers.interfaces.api.user;

import com.loopers.domain.user.UserRegisterRequest;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.example.ExampleV1Dto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "USER V1 API", description = "User API 입니다.")
public interface UserV1ApiSpec {
    @Operation(
            summary = "회원가입",
            description = "ID, 이메일, 생년월일, 성별로 회원가입한다."
    )
    ApiResponse<UserV1Dto.UserResponse> register(UserRegisterRequest requestBody);

    @Operation(
            summary = "회원조회",
            description = "ID로 유저를 조회합니다."
    )
    ApiResponse<UserV1Dto.UserResponse> getUser(Long userId);
}
