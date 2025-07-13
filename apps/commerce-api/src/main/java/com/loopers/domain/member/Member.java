package com.loopers.domain.member;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    String name;

    String account;

    String email;

    String password;

    String birthday;

    String address;


    static String ACCOUNT_PATTERN = "^[a-z0-9]{1,10}$";
    static String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9_+&*-]+\\.)+[a-zA-Z]{2,7}$";
    static String BIRTHDAY_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    static Member create(MemberRegisterRequest registerRequest) {
        Member member = new Member();

        if(!Pattern.matches(ACCOUNT_PATTERN, registerRequest.account())) {
            throw new CoreException(ErrorType.BAD_REQUEST, "아이디 형식이 잘못되었습니다.");
        }
        member.account = registerRequest.account();
        member.name = registerRequest.name();
        member.password = registerRequest.password();

        if(!Pattern.matches(EMAIL_PATTERN, registerRequest.email())) {
            throw new CoreException(ErrorType.BAD_REQUEST, "이메일 형식이 잘못되었습니다.");
        }
        member.email = registerRequest.email();
        if(!Pattern.matches(BIRTHDAY_PATTERN, registerRequest.birthday())) {
            throw new CoreException(ErrorType.BAD_REQUEST, "생일 형식이 잘못되었습니다.");
        }
        member.birthday = registerRequest.birthday();
        member.address = registerRequest.address();

        return member;
    }


}
