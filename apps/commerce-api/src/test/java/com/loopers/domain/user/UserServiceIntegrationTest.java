package com.loopers.domain.user;

import com.loopers.infrastructure.member.UserJpaRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceIntegrationTest {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @DisplayName("회원가입 시도 할 때,")
    @Nested
    class register {
        @DisplayName("모든 정보가 주어졌을때, 유저정보가 DB에 저장된다.")
        @Test
        void registerMember_whenAllMemberInfoAreProvide(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","gil1234@gmail.com", "2020-01-01", Sex.MALE
            );

            //when
            User user = userService.registerMember(request);

            //then
            assertThat(user.getId()).isNotNull();
            User savedUser = userJpaRepository.findAll().getFirst();
            assertAll(
                    () -> assertThat(savedUser.getId()).isEqualTo(user.getId()),
                    () -> assertThat(savedUser.getAccount()).isEqualTo(request.account()),
                    () -> assertThat(savedUser.getEmail()).isEqualTo(request.email()),
                    () -> assertThat(savedUser.getBirthday()).isEqualTo(request.birthday()),
                    () -> assertThat(savedUser.getSex()).isEqualTo(request.sex())
            );
        }

        @DisplayName("이미 해당 아이디로 회원가입된 멤버가 존재시, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsException_whenAlreadyRegisteredMember() {
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","gil1234@gmail.com", "2020-01-01", Sex.MALE
            );
            User user = userJpaRepository.save(
                    User.create(request)
            );

            //when
            CoreException exception = assertThrows(CoreException.class, () -> {
                userService.registerMember(request);
            });

            //then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }
    }
}
