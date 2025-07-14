package com.loopers.interfaces.api.user;

import com.loopers.application.example.ExampleInfo;
import com.loopers.application.user.UserInfo;
import com.loopers.domain.user.Gender;
import com.loopers.interfaces.api.example.ExampleV1Dto;

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
