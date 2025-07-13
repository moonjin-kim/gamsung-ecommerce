package com.loopers.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository memberRepository;
    @InjectMocks
    private UserService userService;

    @DisplayName("회원가입 시도 할 때,")
    @Nested
    class register {
        @DisplayName("모든 정보가 주어졌을때, 유저정보가 DB에 저장된다.")
        @Test
        void registerMember_whenAllMemberInfoAreProvide(){
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","gildong@gmail.com", "2020-01-01", Sex.MALE
            );
            User userToSave = User.create(request);
            when(memberRepository.save(any(User.class))).thenReturn(userToSave);

            //when
            userService.registerMember(request);

            //then
            verify(memberRepository, times(1)).save(any(User.class));
        }
    }
}
