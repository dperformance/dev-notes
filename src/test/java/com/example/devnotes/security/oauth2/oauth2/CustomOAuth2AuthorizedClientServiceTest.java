package com.example.devnotes.security.oauth2.oauth2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import javax.sql.DataSource;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomOAuth2AuthorizedClientServiceTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("org/springframework/security/oauth2/client/oauth2-client-schema.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public ClientRegistrationRepository clientRegistrationRepository() {
            ClientRegistration clientRegistration = ClientRegistration
                    .withRegistrationId("naver")
                    .clientId("test-client-id")
                    .clientSecret("test-client-secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("http://localhost:8888/login/oauth2/code/naver")
                    .scope("name", "email")
                    .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                    .tokenUri("https://nid.naver.com/oauth2.0/token")
                    .userInfoUri("https://openapi.naver.com/v1/nid/me")
                    .userNameAttributeName("response")
                    .clientName("Naver")
                    .build();

            return new InMemoryClientRegistrationRepository(clientRegistration);
        }

        @Bean
        public OAuth2AuthorizedClientService authorizedClientService(
                JdbcTemplate jdbcTemplate,
                ClientRegistrationRepository clientRegistrationRepository) {
            return new JdbcOAuth2AuthorizedClientService(
                    jdbcTemplate,
                    clientRegistrationRepository
            );
        }
    }

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    private OAuth2AuthorizedClient createTestClient() {
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("naver")
                .clientId("test-client-id")
                .clientSecret("test-client-secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8888/login/oauth2/code/naver")
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .clientName("Naver")
                .build();

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                "test-access-token-value",
                Instant.now(),
                Instant.now().plusSeconds(3600));

        OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                "test-refresh-token-value",
                Instant.now().plusSeconds(86400));

        return new OAuth2AuthorizedClient(
                clientRegistration,
                "test-principal-name",
                accessToken,
                refreshToken
        );
    }

    @Test
    void verifyTokenSavingPoint() {
        OAuth2AuthorizedClient client = createTestClient();
        Authentication auth = new TestingAuthenticationToken("test-user", "password");

        clientService.saveAuthorizedClient(client, auth);

        OAuth2AuthorizedClient savedClient = clientService.loadAuthorizedClient(
                "naver",
                "test-user"
        );

        assertNotNull(savedClient, "저장된 클라이언트가 null이 아니어야 합니다");
        assertEquals("test-access-token-value",
                savedClient.getAccessToken().getTokenValue(),
                "액세스 토큰 값이 일치해야 합니다");
        assertEquals("test-refresh-token-value",
                savedClient.getRefreshToken().getTokenValue(),
                "리프레시 토큰 값이 일치해야 합니다");
    }

    @Test
    void verifyTokenExpiration() {
        OAuth2AuthorizedClient client = createTestClient();
        Authentication auth = new TestingAuthenticationToken("test-user", "password");

        clientService.saveAuthorizedClient(client, auth);

        OAuth2AuthorizedClient savedClient = clientService.loadAuthorizedClient(
                "naver",
                "test-user"
        );

        assertTrue(savedClient.getAccessToken().getExpiresAt().isAfter(Instant.now()),
                "액세스 토큰이 아직 만료되지 않아야 합니다");
    }

    @Test
    void verifyTokenRemoval() {
        OAuth2AuthorizedClient client = createTestClient();
        Authentication auth = new TestingAuthenticationToken("test-user", "password");

        clientService.saveAuthorizedClient(client, auth);
        clientService.removeAuthorizedClient("naver", "test-user");

        OAuth2AuthorizedClient removedClient = clientService.loadAuthorizedClient(
                "naver",
                "test-user"
        );

        assertNull(removedClient, "삭제된 클라이언트는 null이어야 합니다");
    }
}