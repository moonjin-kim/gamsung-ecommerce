package com.loopers.domain.point;

import com.loopers.domain.user.Sex;
import com.loopers.domain.user.User;
import com.loopers.domain.user.UserRegisterRequest;
import com.loopers.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PointTest {

    @DisplayName("포인트 충전할 때")
    @Nested
    class Charge {
        @DisplayName("")
        @Test
        void chargePoint(){
            //given
            User user = UserFixture.createMember();

            //when

            //then
        }
    }
}
