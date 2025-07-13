package com.loopers.domain.member;

import com.loopers.domain.example.ExampleModel;
import com.loopers.infrastructure.member.MemberJpaRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.bouncycastle.asn1.x500.style.RFC4519Style.member;

@SpringBootTest
class MemberServiceIntegrationTest {
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private MemberService memberService;

    @DisplayName("회원가입 시도 할 때,")
    @Nested
    class register {
        @DisplayName("모든 정보가 주어졌을때, 유저정보가 DB에 저장된다.")
        @Test
        void registerMember_whenAllMemberInfoAreProvide(){
            //given
            MemberRegisterRequest request = new MemberRegisterRequest(
                    "gil123","홍길동", "gil1234", "gildong@gmail.com","2020-01-01", "서울특별시"
            );

            //when
            Member member = memberService.registerMember(request);

            //then
            assertThat(member.getId()).isNotNull();
            Member savedMember = memberJpaRepository.findAll().getFirst();
            assertAll(
                    () -> assertThat(savedMember.getId()).isEqualTo(member.getId()),
                    () -> assertThat(savedMember.getAccount()).isEqualTo(request.account()),
                    () -> assertThat(savedMember.getName()).isEqualTo(request.name()),
                    () -> assertThat(savedMember.getEmail().address()).isEqualTo(request.email()),
                    () -> assertThat(savedMember.getPassword()).isEqualTo(request.password()),
                    () -> assertThat(savedMember.getBirthday()).isEqualTo(request.birthday()),
                    () -> assertThat(savedMember.getAddress()).isEqualTo(request.address())
            );
        }

        @DisplayName("이미 해당 아이디로 회원가입된 멤버가 존재시, BAD_REQUEST 예외가 발생한다.")
        @Test
        void throwsException_whenAlreadyRegisteredMember() {
            //given
            MemberRegisterRequest request = new MemberRegisterRequest(
                    "gil123","홍길동", "gil1234", "gildong@gmail.com","2020-01-01", "서울특별시"
            );
            Member member = memberJpaRepository.save(
                    Member.create(request)
            );

            //when
            CoreException exception = assertThrows(CoreException.class, () -> {
                memberService.registerMember(request);
            });

            //then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
        }
    }
}
