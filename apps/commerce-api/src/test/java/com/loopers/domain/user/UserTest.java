package com.loopers.domain.user;

import com.loopers.domain.example.ExampleModel;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @DisplayName("회원가입할 때")
    @Nested
    class Register {
        @DisplayName("회원 정보가 모두 주어지면 회원가입에 성공한다.")
        @Test
        void registerMember(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","홍길동", "gil1234", "gildong@gmail.com","2020-01-01", "서울특별시"
            );

            //when
            User user = User.create(request);

            //then
            assertThat(user.getId()).isNotNull();
            assertThat(user.getAccount()).isEqualTo(request.account());
            assertThat(user.getName()).isEqualTo(request.name());
            assertThat(user.getEmail().address()).isEqualTo(request.email());
            assertThat(user.getPassword()).isEqualTo(request.password());
            assertThat(user.getBirthday()).isEqualTo(request.birthday());
            assertThat(user.getAddress()).isEqualTo(request.address());
        }

        @DisplayName("아이디가 영문 숫자 10자리 초과이면, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsBadRequestException_whenAccountLenOverTen(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123123112","홍길동", "gil1234", "gildong@gmail.com","2020-01-01", "서울특별시"
            );

            //when
            CoreException result = assertThrows(CoreException.class, () -> {
                User.create(request);
            });

            //then
            assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }

        @DisplayName("이메일 형식이 잘못되면, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsBadRequestException_whenIncorrectEmailFormat(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil1231231","홍길동", "gil1234", "gildong","2020-01-01", "서울특별시"
            );

            //when
            CoreException result = assertThrows(CoreException.class, () -> {
                User.create(request);
            });

            //then
            assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }

        @DisplayName("생일 형식이 잘못되면, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsBadRequestException_whenIncorrectBirthDayFormat(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil1231231","홍길동", "gil1234", "gildong@gmail.com","2020-01", "서울특별시"
            );

            //when
            CoreException result = assertThrows(CoreException.class, () -> {
                User.create(request);
            });

            //then
            assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }
    }
}
