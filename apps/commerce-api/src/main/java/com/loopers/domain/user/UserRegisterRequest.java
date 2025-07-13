package com.loopers.domain.user;

public record UserRegisterRequest(
        String account,
        String email,
        String birthday,
        Sex sex
) {
}
