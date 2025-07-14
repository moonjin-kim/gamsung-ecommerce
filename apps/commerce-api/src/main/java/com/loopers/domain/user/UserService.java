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
    private final UserRepository userRepository;

    @Transactional
    public User registerMember(UserRegisterRequest request) {
        checkDuplicateAccount(request);

        return userRepository.save(User.register(request));
    }

    @Transactional(readOnly = true)
    public User getUser(String account) {
        return userRepository.findByAccount(account).orElseThrow(() ->
                new CoreException(ErrorType.NOT_FOUND, "[account = " + account + "] 존재하지 않는 회원입니다.")
        );
    }

    private void checkDuplicateAccount(UserRegisterRequest request) {
        if (userRepository.findByAccount(request.account()).isPresent()) {
            throw new CoreException(ErrorType.BAD_REQUEST,"이미 존재하는 아이디입니다: " + request.account());
        }
    }
}
