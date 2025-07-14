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
    @Column(length = 50, nullable = false)
    String email;
    @Column
    LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Gender gender;


    static String ACCOUNT_PATTERN = "^[a-zA-Z0-9]{1,10}$";
    static String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9_+&*-]+\\.)+[a-zA-Z]{2,7}$";

    public static User create(UserRegisterRequest registerRequest) {
        User user = new User();

        user.setEmail(registerRequest.email());
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

    private void setEmail(String email) {
        if(email == null || email.isEmpty()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "이메일은 비어있을 수 없습니다.");
        }

        if(!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new CoreException(ErrorType.BAD_REQUEST, "아이디 형식이 잘못되었습니다.");
        }
        this.email = email;
    }

    private void setBirthdayFromString(String birthday) {
        if(birthday == null || birthday.isEmpty()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "생년월일은 비어있을 수 없습니다.");
        }

        try {
            this.birthday = LocalDate.parse(birthday);
        } catch (DateTimeParseException e) {
            throw new CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 잘못되었습니다.");
        }
    }


}
