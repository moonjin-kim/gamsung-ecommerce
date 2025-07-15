package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserInfo;
import com.loopers.domain.user.Gender;

import java.time.LocalDate;

public class UserV1Dto {
    public record UserResponse(
            Long id,
            String account,
            String email,
            LocalDate birthday,
            Gender gender
    ){
        public static UserV1Dto.UserResponse from(UserInfo info) {
            return new UserV1Dto.UserResponse(
                    info.id(),
                    info.account(),
                    info.email(),
                    info.birthday(),
                    info.gender()
            );
        }
    }
}
