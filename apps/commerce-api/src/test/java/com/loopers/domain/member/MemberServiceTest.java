package com.loopers.domain.member;

import com.loopers.domain.example.ExampleService;
import com.loopers.infrastructure.example.ExampleJpaRepository;
import com.loopers.infrastructure.member.MemberJpaRepository;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @DisplayName("회원가입 시도 할 때,")
    @Nested
    class register {
        @DisplayName("모든 정보가 주어졌을때, 회원가입에 성공한다.")
        @Test
        void registerMember_whenAllMemberInfoAreProvide(){
            //given
            MemberRegisterRequest request = new MemberRegisterRequest(
                    "gil123","홍길동", "gil1234", "gildong@gmail.com","2020-01-01", "서울특별시"
            );

            //when
            Member member = memberService.registerMember(request);

            //then
            assertAll(
                    ()-> assertThat(member.getId()).isNotNull(),
                    ()-> assertThat(member.getAccount()).isEqualTo(request.account()),
                    ()-> assertThat(member.getAddress()).isEqualTo(request.address()),
                    ()-> assertThat(member.getName()).isEqualTo(request.name()),
                    ()-> assertThat(member.getPassword()).isEqualTo(request.password()),
                    ()-> assertThat(member.getEmail()).isEqualTo(request.email()),
                    ()-> {
                        assertThat(member.getBirthday()).isEqualTo(request.birthday());
                    }
            );
        }
    }
}
