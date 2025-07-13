package com.loopers.domain.member;

import com.loopers.domain.example.ExampleModel;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {
    @DisplayName("회원가입할 때")
    @Nested
    class Register {
        @DisplayName("회원 정보가 모두 주어지면 회원가입에 성공한다.")
        @Test
        void registerMember(){
            //given
            MemberRegisterRequest request = new MemberRegisterRequest(
                    "gil123","홍길동", "gil1234", "gildong@gmail.com","2020-01-01", "서울특별시"
            );

            //when
            Member member = Member.create(request);

            //then
            assertThat(member.getId()).isNotNull();
            assertThat(member.getAccount()).isEqualTo(request.account());
            assertThat(member.getName()).isEqualTo(request.name());
            assertThat(member.getEmail()).isEqualTo(request.email());
            assertThat(member.getPassword()).isEqualTo(request.password());
            assertThat(member.getBirthday()).isEqualTo(request.birthday());
            assertThat(member.getAddress()).isEqualTo(request.address());
        }

        @DisplayName("아이디가 영문 숫자 10자리 초과이면, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsBadRequestException_whenAccountLenOverTen(){
            //given
            MemberRegisterRequest request = new MemberRegisterRequest(
                    "gil123123112","홍길동", "gil1234", "gildong@gmail.com","2020-01-01", "서울특별시"
            );

            //when
            CoreException result = assertThrows(CoreException.class, () -> {
                Member.create(request);
            });

            //then
            assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }
    }
}
