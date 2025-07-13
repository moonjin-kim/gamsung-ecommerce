package com.loopers.application.user;

import com.loopers.domain.user.User;
import com.loopers.domain.user.Sex;

import java.time.LocalDate;

public record UserInfo(
        Long id,
        String account,
        String name,
        String email,
        LocalDate birthday,
        String address,
        Sex sex
) {
    public static UserInfo from(User user) {
        return new UserInfo(
                user.getId(),
                user.getAccount(),
                user.getName(),
                user.getEmail().address(),
                user.getBirthday(),
                user.getAddress(),
                user.getSex()
        );
    }
}
