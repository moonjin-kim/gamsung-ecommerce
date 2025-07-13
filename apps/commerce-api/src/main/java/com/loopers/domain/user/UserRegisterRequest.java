package com.loopers.domain.user;

public record UserRegisterRequest(
        String account,
        String name,
        String password,
        String email,
        String birthday,
        String address
) {
}
