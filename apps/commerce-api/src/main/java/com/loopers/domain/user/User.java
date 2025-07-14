package com.loopers.domain.user;

import com.loopers.domain.BaseEntity;
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
@Table(name = "user")
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

    private static final String ACCOUNT_PATTERN = "^[a-zA-Z0-9]{1,10}$";
    private static final String BIRTH_DATE_REGEX =
            "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    public static User create(UserRegisterRequest registerRequest) {
        User user = new User();
        if(registerRequest.email() == null || registerRequest.email().isEmpty()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "이메일은 비어있을 수 없습니다.");
        }
        user.email = new Email(registerRequest.email());

        user.setAccount(registerRequest.account());
        user.setBirthdayFromString(registerRequest.birthday());

        if (registerRequest.gender() == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "성별은 비어있을 수 없습니다.");
        }
        user.gender = registerRequest.gender();

        return user;
    }

    private void setAccount(String account) {
        if(account == null || account.isEmpty()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "아이디는 비어있을 수 없습니다.");
        }
        if(!Pattern.matches(ACCOUNT_PATTERN, account)) {
            throw new CoreException(ErrorType.BAD_REQUEST, "아이디 형식이 잘못되었습니다.");
        }
        this.account = account;
    }

    private void setBirthdayFromString(String birthday) {
        if(birthday == null || birthday.isEmpty()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "생년월일은 비어있을 수 없습니다.");
        }

        if(!Pattern.matches(BIRTH_DATE_REGEX, birthday)) {
            throw new CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 잘못되었습니다.");
        }

        this.birthday = LocalDate.parse(birthday);
    }


}
