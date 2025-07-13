package com.loopers.domain.member;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member registerMember(MemberRegisterRequest request) {
        if (memberRepository.findByAccount(request.account()).isPresent()) {
            throw new CoreException(ErrorType.BAD_REQUEST,"이미 존재하는 아이디입니다: " + request.account());
        }

        return memberRepository.save(Member.create(request));
    }
}
