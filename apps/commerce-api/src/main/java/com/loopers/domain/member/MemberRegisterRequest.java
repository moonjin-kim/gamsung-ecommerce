package com.loopers.domain.member;

public record MemberRegisterRequest(
        String account,
        String name,
        String password,
        String email,
        String birthday,
        String address
) {
}
