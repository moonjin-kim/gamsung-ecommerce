package com.loopers.domain.point;

import com.loopers.domain.user.User;
import com.loopers.domain.user.UserService;
import com.loopers.fixture.UserFixture;
import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.infrastructure.user.UserJpaRepository;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PointServiceIntegrationTest {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private PointJpaRepository pointJpaRepository;
    @Autowired
    private PointService pointService;
    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @DisplayName("포인트를 충전할 때")
    @Nested
    class ChargePoint {
        @DisplayName("해당 ID에 포인트가 충전된다.")
        @Test
        void returnPoint(){
            //given
            User user = userJpaRepository.save(UserFixture.createMember());
            int amount = 10000;

            //when
            Point result = pointService.chargePoint(user.getId(), amount);

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.getUser().getId()).isEqualTo(user.getId()),
                    () -> assertThat(result.getAmount()).isEqualTo(amount),
                    () -> assertThat(result.getBalance()).isEqualTo(0 + amount)
            );
        }

        @DisplayName("해당 ID에 잔액이 남아있으면 잔액에 충전 포인트가 더해진다.")
        @Test
        void addPoint_whenRemainBalance(){
            //given
            User user = userJpaRepository.save(UserFixture.createMember());
            Point chargedPoint = pointJpaRepository.save(
                    Point.charge(user, 1000, 0)
            );
            int amount = 10000;

            //when
            Point result = pointService.chargePoint(user.getId(), amount);

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.getUser().getId()).isEqualTo(user.getId()),
                    () -> assertThat(result.getAmount()).isEqualTo(amount),
                    () -> assertThat(result.getBalance()).isEqualTo(chargedPoint.getBalance() + amount)
            );
        }

        @DisplayName("존재하지 않는 유저 ID 로 충전을 시도한 경우, 실패한다.")
        @Test
        void throwException_whenInvalidIdIsProvided(){
            //given
            int amount = 10000;

            //when
            CoreException exception = assertThrows(CoreException.class, () -> {
                pointService.chargePoint(-1L, amount);
            });

            //then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        }
    }
}
