package com.loopers.application.user;

import com.loopers.domain.user.UserService;
import com.loopers.interfaces.api.user.UserV1RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {
    private final UserService userService;

    public UserInfo registerUser(UserV1RequestDto.UserRegisterRequest request) {
        return UserInfo.from(userService.registerMember(request));
    }

    public UserInfo getUser(String account) {
        return UserInfo.from(userService.getUser(account));
    }
}
