package com.loopers.domain.user;

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
                    "gil123", "gil1234@gmail.com", "2020-01-01", Sex.MALE
            );

            //when
            User user = User.create(request);

            //then
            assertThat(user.getId()).isNotNull();
            assertThat(user.getAccount()).isEqualTo(request.account());
            assertThat(user.getEmail()).isEqualTo(request.email());
            assertThat(user.getBirthday()).isEqualTo(request.birthday());
            assertThat(user.getSex()).isEqualTo(request.sex());

        }

        @DisplayName("아이디가 영문 숫자 10자리 초과이면, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsBadRequestException_whenAccountLenOverTen(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil12312312","gildong@gmail.com", "2020-01-01", Sex.MALE
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
                    "gil123","gildong",  "2020-01-01", Sex.MALE
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
                    "gil123","gildong@gmail.com", "2020-01", Sex.MALE
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
