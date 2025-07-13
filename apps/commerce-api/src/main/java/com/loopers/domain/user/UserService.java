package com.loopers.domain.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository memberRepository;

    @Transactional
    public User registerMember(UserRegisterRequest request) {
        checkDuplicateAccount(request);

        return memberRepository.save(User.create(request));
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return new User();
    }

    private void checkDuplicateAccount(UserRegisterRequest request) {
        if (memberRepository.findByAccount(request.account()).isPresent()) {
            throw new CoreException(ErrorType.BAD_REQUEST,"이미 존재하는 아이디입니다: " + request.account());
        }
    }
}
