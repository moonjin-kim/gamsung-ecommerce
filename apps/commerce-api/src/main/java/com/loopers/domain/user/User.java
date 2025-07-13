package com.loopers.domain.user;

import com.loopers.domain.BaseEntity;
import com.loopers.domain.Email;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(length = 100, nullable = false)
    String account;
    @Column(length = 100, nullable = false)
    String name;
    @Embedded
    Email email;
    @Column(length = 100, nullable = false)
    String password;
    @Column
    LocalDate birthday;
    @Column(length = 100, nullable = false)
    String address;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Sex sex;


    static String ACCOUNT_PATTERN = "^[a-z0-9]{1,10}$";

    static User create(UserRegisterRequest registerRequest) {
        User user = new User();

        if(!Pattern.matches(ACCOUNT_PATTERN, registerRequest.account())) {
            throw new CoreException(ErrorType.BAD_REQUEST, "아이디 형식이 잘못되었습니다.");
        }
        user.account = registerRequest.account();

        user.name = registerRequest.name();
        user.password = registerRequest.password();
        user.email = new Email(registerRequest.email());
        user.birthday = parseStringToLocalDate(registerRequest);
        user.address = registerRequest.address();

        return user;
    }

    private static LocalDate parseStringToLocalDate(UserRegisterRequest registerRequest) {
        try {
            return LocalDate.parse(registerRequest.birthday());
        } catch (DateTimeParseException e) {
            throw new CoreException(ErrorType.BAD_REQUEST, "생일 형식이 잘못되었습니다.");
        }
    }


}
