package com.loopers.fixture;

import com.loopers.domain.user.Sex;
import com.loopers.domain.user.User;
import com.loopers.domain.user.UserRegisterRequest;

public class UserFixture {
    public static UserRegisterRequest createUserRegisterRequest() {
        return new UserRegisterRequest(
                "gil123", "gil1234@gmail.com", "2020-01-01", Sex.MALE
        );
    }

    public static User createMember() {
        return User.register(createUserRegisterRequest());
    }
}
