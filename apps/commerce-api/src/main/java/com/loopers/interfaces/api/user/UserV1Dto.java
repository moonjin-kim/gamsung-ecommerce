package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserInfo;
import com.loopers.domain.user.Sex;

import java.time.LocalDate;

public class UserV1Dto {
    public record UserResponse(
            Long id,
            String account,
            String name,
            String email,
            LocalDate birthday,
            String address,
            Sex sex
    ){
        public static UserV1Dto.UserResponse from(UserInfo info) {
            return new UserV1Dto.UserResponse(
                    info.id(),
                    info.account(),
                    info.name(),
                    info.email(),
                    info.birthday(),
                    info.address(),
                    info.sex()
            );
        }
    }
}
