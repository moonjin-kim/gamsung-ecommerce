package com.loopers.interfaces.api.user;

import com.loopers.domain.user.Sex;
import com.loopers.domain.user.User;
import com.loopers.domain.user.UserRegisterRequest;
import com.loopers.infrastructure.user.UserJpaRepository;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserV1ApiE2ETest {
    private static final String ENDPOINT_REGISTER = "/api/v1/users";
    private static final String ENDPOINT_GET_ME = "/api/v1/users/me";

    private final UserJpaRepository userJpaRepository;
    private final TestRestTemplate testRestTemplate;
    private final DatabaseCleanUp databaseCleanUp;

    @Autowired
    public UserV1ApiE2ETest(
            UserJpaRepository userJpaRepository,
            TestRestTemplate testRestTemplate,
            DatabaseCleanUp databaseCleanUp
    ) {
        this.userJpaRepository = userJpaRepository;
        this.testRestTemplate = testRestTemplate;
        this.databaseCleanUp = databaseCleanUp;
    }

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @DisplayName("Post /api/v1/users")
    @Nested
    class RegisterUser {

        @DisplayName("올바른 회원정보를 포함하여 회원가입 요청시, 회원가입한 유저 정보를 받는다.")
        @Test
        void returnsUserInfo_whenValidBodyIsProvided() {
            //given
            UserRegisterRequest request = new UserRegisterRequest(
                    "gil123","gildong@gmail.com", "2020-01-01", Sex.MALE
            );

            //when
            ParameterizedTypeReference<ApiResponse<UserV1Dto.UserResponse>> responseType = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<ApiResponse<UserV1Dto.UserResponse>> response =
                    testRestTemplate.exchange(
                            ENDPOINT_REGISTER,
                            HttpMethod.POST,
                            new HttpEntity<UserRegisterRequest>(request),
                            responseType
                    );

            //then
            assertAll(
                    () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
                    () -> assertThat(response.getBody().data().id()).isNotNull(),
                    () -> assertThat(response.getBody().data().account()).isEqualTo(request.account())
            );
        }

        @DisplayName("회원가입 시 잘못된 값이 들어오면, 400 BAD_REQUEST 응답을 받는다.")
        @ParameterizedTest
        @MethodSource("provideInvalidUserRegisterRequests")
        void returnsBadRequest_whenInvalidBodyIsProvided(UserRegisterRequest invalidRequest) {
            // given
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserRegisterRequest> requestEntity = new HttpEntity<>(invalidRequest, headers);

            // when
            ResponseEntity<Void> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class
            );

            // then
            assertAll(
                    () -> assertTrue(response.getStatusCode().is4xxClientError()),
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
            );
        }


        private static Stream<Arguments> provideInvalidUserRegisterRequests() {
            return Stream.of(
                    Arguments.of(new UserRegisterRequest(null, "test@email.com", "2000-01-01", Sex.MALE)), //아이디 없음
                    Arguments.of(new UserRegisterRequest("short", null, "2000-01-01", Sex.MALE)), // 이메일 없음
                    Arguments.of(new UserRegisterRequest("hong123", "test@email.com", null, Sex.MALE)), //생년월일 없음
                    Arguments.of(new UserRegisterRequest("hong1234", "test@email.com", "2000-01-01", null))  //성별없ㅇ므
            );
        }
    }

    @DisplayName("Get /api/v1/users/me")
    @Nested
    class GetUsers {

        @DisplayName("내 정보 조회에 성공할 경우, 해당하는 유저 정보를 응답으로 반환한다.")
        @Test
        void returnsUserInfo_whenValidIdIsProvided() {
            //given
            User user = userJpaRepository.save(
                    User.register(
                            new UserRegisterRequest(
                                    "gil123","gil1234@gmail.com", "2020-01-01", Sex.MALE
                            )
                    )
            );
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-USER-ID", "1");

            //when
            ParameterizedTypeReference<ApiResponse<UserV1Dto.UserResponse>> responseType = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<ApiResponse<UserV1Dto.UserResponse>> response =
                    testRestTemplate.exchange(
                            ENDPOINT_GET_ME,
                            HttpMethod.GET,
                            new HttpEntity<>(null, headers),
                            responseType
                    );

            //then
            assertAll(
                    () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
                    () -> assertThat(response.getBody().data().id()).isEqualTo(user.getId()),
                    () -> assertThat(response.getBody().data().account()).isEqualTo(user.getAccount()),
                    () -> assertThat(response.getBody().data().birthday()).isEqualTo(user.getBirthday()),
                    () -> assertThat(response.getBody().data().email()).isEqualTo(user.getEmail()),
                    () -> assertThat(response.getBody().data().sex()).isEqualTo(user.getSex())
            );
        }

        @DisplayName("존재하지 않는 ID로 조회할 경우, 404 Not Found 응답을 반환한다.")
        @Test
        void throwsException_whenInvalidIdIsProvided() {
            //given
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-USER-ID", "-1");

            //when
            ParameterizedTypeReference<ApiResponse<UserV1Dto.UserResponse>> responseType = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<ApiResponse<UserV1Dto.UserResponse>> response =
                    testRestTemplate.exchange(
                            ENDPOINT_GET_ME,
                            HttpMethod.GET,
                            new HttpEntity<>(null, headers),
                            responseType
                    );

            //then
            assertAll(
                    () -> assertTrue(response.getStatusCode().is4xxClientError()),
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
            );
        }
    }
}
