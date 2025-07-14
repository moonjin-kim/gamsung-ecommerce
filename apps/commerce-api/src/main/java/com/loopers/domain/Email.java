package com.loopers.domain;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Email(@Column(name = "email", length = 150, nullable = false)String address) {
    static String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9_+&*-]+\\.)+[a-zA-Z]{2,7}$";
    public Email {
        if(address == null || address.isEmpty()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "이메일은 비어있을 수 없습니다.");
        }
        if(!Pattern.matches(EMAIL_PATTERN, address)) {
            throw new CoreException(ErrorType.BAD_REQUEST, "이메일 형식이 잘못되었습니다.");
        }
    }
}
