package com.loopers.interfaces.api.user;

import com.loopers.domain.user.Gender;
import jakarta.validation.constraints.NotNull;

public class UserV1RequestDto {
    public record UserRegisterRequest(
            @NotNull
            String account,
            @NotNull
            String email,
            @NotNull
            String birthday,
            @NotNull
            Gender sex
    ) {
    }
}
