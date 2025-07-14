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
            assertThat(user.getEmail().address()).isEqualTo(request.email());
            assertThat(user.getBirthday()).isEqualTo(request.birthday());
            assertThat(user.getSex()).isEqualTo(request.sex());

        }

        @DisplayName("ID 가 영문 및 숫자 10자 이내 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @Test
        void throwsBadRequestException_whenAccountLenOverTen(){
            //given
            UserRegisterRequest request1 = new UserRegisterRequest(
                    "gil12312312","gildong@gmail.com", "2020-01-01", Gender.MALE
            );
            UserRegisterRequest request2 = new UserRegisterRequest(
                    "홍길동12312312","gildong@gmail.com", "2020-01-01", Gender.MALE
            );

            //when
            CoreException result1 = assertThrows(CoreException.class, () -> {
                User.create(request1);
            });
            CoreException result2 = assertThrows(CoreException.class, () -> {
                User.create(request2);
            });

            //then
            assertThat(result1.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(result2.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }

        @DisplayName("이메일이 xx@yy.zz 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @Test
        void throwsBadRequestException_whenIncorrectEmailFormat(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","gildong",  "2020-01-01", Gender.MALE
            );

            //when
            CoreException result = assertThrows(CoreException.class, () -> {
                User.create(request);
            });

            //then
            assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }

        @DisplayName("생년월일이 yyyy-MM-dd 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @Test
        void throwsBadRequestException_whenIncorrectBirthDayFormat(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","gildong@gmail.com", "2020-01", Gender.MALE
            );

            //when
            CoreException result = assertThrows(CoreException.class, () -> {
                User.create(request);
            });

            //then
            assertThat(result.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }

        @DisplayName("성별을 받지 못하면, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsBadRequestException_whenIncorrectSex(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","gildong@gmail.com", "2020-01", null
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
