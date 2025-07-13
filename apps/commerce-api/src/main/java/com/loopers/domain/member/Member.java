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


    static Member create(MemberRegisterRequest registerRequest) {
        Member member = new Member();

        member.account = registerRequest.account();
        member.name = registerRequest.name();
        member.password = registerRequest.password();
        member.email = registerRequest.email();
        member.birthday = registerRequest.birthday();
        member.address = registerRequest.address();

        return member;
    }


}
