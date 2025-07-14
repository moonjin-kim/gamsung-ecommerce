package com.loopers.application.user;

import com.loopers.domain.user.UserRegisterRequest;
import com.loopers.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {
    private final UserService userService;

    public UserInfo registerUser(UserRegisterRequest request) {
        return UserInfo.from(userService.registerMember(request));
    }

    public UserInfo getUser(String account) {
        return UserInfo.from(userService.getUser(account));
    }
}
