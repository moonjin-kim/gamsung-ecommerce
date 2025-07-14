package com.loopers.fixture;

import com.loopers.domain.user.Gender;
import com.loopers.domain.user.User;
import com.loopers.interfaces.api.user.UserV1RequestDto;

public class UserFixture {
    public static UserV1RequestDto.UserRegisterRequest createUserRegisterRequest() {
        return new UserV1RequestDto.UserRegisterRequest(
                "gil123", "gil1234@gmail.com", "2020-01-01", Gender.MALE
        );
    }

    public static User createMember() {
        return User.register(createUserRegisterRequest());
    }
}
