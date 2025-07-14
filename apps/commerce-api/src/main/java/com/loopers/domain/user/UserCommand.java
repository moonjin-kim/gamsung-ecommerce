package com.loopers.domain.user;

import com.loopers.interfaces.api.user.UserV1RequestDto;

public class UserCommand {
    public record Create(
            String account,
            String email,
            String birthday,
            Gender gender
    ) {
    }
}
