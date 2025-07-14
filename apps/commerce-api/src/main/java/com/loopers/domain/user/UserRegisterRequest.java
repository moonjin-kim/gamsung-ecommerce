package com.loopers.domain.user;

import jakarta.validation.constraints.NotNull;

public record UserRegisterRequest(
        @NotNull
        String account,
        @NotNull
        String email,
        @NotNull
        String birthday,
        @NotNull
        Sex sex
) {
}
