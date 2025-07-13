package com.loopers.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member registerMember(MemberRegisterRequest request) {

        return new Member();
    }
}
