package com.loopers.interfaces.api.user;

import com.loopers.domain.user.Gender;
import com.loopers.domain.user.UserCommand;
import jakarta.validation.constraints.NotNull;

public class UserV1RequestDto {
    public record Register(
            @NotNull
            String account,
            @NotNull
            String email,
            @NotNull
            String birthday,
            @NotNull
            GenderRequest gender
    ) {
        public UserCommand.Create toCommand() {
            return new UserCommand.Create(
                    account,
                    email,
                    birthday,
                    Gender.from(gender)
            );
        }
    }

    public enum GenderRequest {
        MALE,
        FEMALE
    }
}
