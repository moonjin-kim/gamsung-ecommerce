package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserFacade;
import com.loopers.application.user.UserInfo;
import com.loopers.domain.user.UserRegisterRequest;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserV1Controller implements UserV1ApiSpec {

    private final UserFacade userFacade;

    @PostMapping("")
    @Override
    public ApiResponse<UserV1Dto.UserResponse> register(@RequestBody UserRegisterRequest body) {
        UserInfo info = userFacade.registerUser(body);
        UserV1Dto.UserResponse response = UserV1Dto.UserResponse.from(info);
        return ApiResponse.success(response);
    }

    @GetMapping("/{userId}")
    @Override
    public ApiResponse<UserV1Dto.UserResponse> getUser(@PathVariable(value = "userId") Long userId) {
        UserInfo info = userFacade.getUser(userId);
        UserV1Dto.UserResponse response = UserV1Dto.UserResponse.from(info);
        return ApiResponse.success(response);
    }
}
