package com.loopers.domain.point;

import com.loopers.domain.user.User;
import com.loopers.fixture.UserFixture;
import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.infrastructure.user.UserJpaRepository;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class PointServiceIntegrationTest {
    @MockitoSpyBean
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
            Point result = pointService.chargePoint(user, amount);

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
            Point result = pointService.chargePoint(user, amount);

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.getUser().getId()).isEqualTo(user.getId()),
                    () -> assertThat(result.getAmount()).isEqualTo(amount),
                    () -> assertThat(result.getBalance()).isEqualTo(chargedPoint.getBalance() + amount)
            );
        }
    }

    @DisplayName("포인트 조회시")
    @Nested
    class getBalance {

        @DisplayName("보유 포인트가 존재하면, 보유 포인트가 반환된다.")
        @Test
        void returnLastPoint_whenValidIdIsProvided(){
            //given
            User user = userJpaRepository.save(
                    UserFixture.createMember()
            );
            Point point = pointJpaRepository.save(
                    Point.charge(user, 10000, 0)
            );

            //when
            Point result = pointService.getLastPoint(user).orElse(null);

            //then
            assertThat(result.balance).isEqualTo(point.balance);
        }

        @DisplayName("보유 포인트가 존재하지 않으면, null이 반환된다.")
        @Test
        void returnNullPoint_whenValidIdIsProvidedAndNoChargeHistory(){
            //given
            User user = userJpaRepository.save(
                    UserFixture.createMember()
            );

            //when
            Optional<Point> result = pointService.getLastPoint(user);

            //then
            assertThat(result.isPresent()).isFalse();
        }

        @DisplayName("존재하지 않는 유저이면, null이 반환된다.")
        @Test
        void returnNullPoint_whenUserNotExist(){
            //given

            //when
            Optional<Point> result = pointService.getLastPoint(null);

            //then
            assertThat(result.isPresent()).isFalse();
        }
    }
}
