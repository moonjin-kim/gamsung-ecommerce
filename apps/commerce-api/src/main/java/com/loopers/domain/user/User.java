package com.loopers.domain.user;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.Email;
import com.loopers.interfaces.api.user.UserV1RequestDto;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(length = 100, nullable = false)
    String account;
    @Embedded
    Email email;
    @Column
    LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Gender gender;

    public static User register(UserV1RequestDto.UserRegisterRequest registerRequest) {
        User user = new User();

        user.email = new Email(registerRequest.email());

        UserValidator.validateAccount(registerRequest.account());
        user.account = registerRequest.account();

        UserValidator.validateBirthday(registerRequest.birthday());
        user.birthday = LocalDate.parse(registerRequest.birthday());

        if (registerRequest.gender() == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "성별은 비어있을 수 없습니다.");
        }
        UserValidator.validateGender(registerRequest.gender());
        user.gender = registerRequest.gender();

        return user;
    }


}
