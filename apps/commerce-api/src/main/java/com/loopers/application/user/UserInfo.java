package com.loopers.application.user;

import com.loopers.domain.user.Sex;
import com.loopers.domain.user.User;

import java.time.LocalDate;

public record UserInfo(
        Long id,
        String account,
        String email,
        LocalDate birthday,
        Sex sex
) {
    public static UserInfo from(User user) {
        return new UserInfo(
                user.getId(),
                user.getAccount(),
                user.getEmail(),
                user.getBirthday(),
                user.getSex()
        );
    }
}
