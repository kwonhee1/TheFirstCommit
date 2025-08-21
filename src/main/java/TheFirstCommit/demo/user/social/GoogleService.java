package TheFirstCommit.demo.user.social;

import TheFirstCommit.demo.user.dto.response.ResponseTokenDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GoogleService {

    private final UserService userService;

    @Value("${social.google.token-uri}")
    private String AccessTokenURI;
    @Value("${social.google.user-info-uri}")
    private String UserInfoURI;

    private final String RedirectURI;

    @Value("${google.client-id}")
    private String ClientId;
    @Value("${google.client-secret}")
    private String ClientSecret;

    public GoogleService(UserService userService, @Value("${FRONT_DOMAIN}") String domain) {
        this.userService = userService;
        RedirectURI = String.format("%s/social/google", domain);
    }

    public ResponseTokenDto socialLogin(String code) {
        String accessToken = getAccessTokenFromGoogle(code);

        Map<String, String> info = getUserInfoFromGoogle(accessToken);

        return userService.login(info.get("id"), RegisterDto
            .builder()
            .provider("google")
            .socialId(info.get("id"))
            // .email(info.get("email"))
            .name(info.get("name"))
            // .nickName(info.get("given_name"))
            // .birth(info.get("birth"))
            .imgURL(info.get("picture"))
            .build()
        );
    }

    private String getAccessTokenFromGoogle(String code) {
        Map<String, Object> googleTokenResponse = WebClient.create(AccessTokenURI).post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("client_id", ClientId)
                .queryParam("client_secret", ClientSecret)
                .queryParam("code", code)
                .queryParam("redirect_uri", RedirectURI)
                .queryParam("grant_type", "authorization_code")
                .build())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header(HttpHeaders.CONTENT_LENGTH, "0")
            //.contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .block();

        return (String) googleTokenResponse.get("access_token");
    }

    private Map<String, String> getUserInfoFromGoogle(String accessToken) {
        Map response1 = WebClient.create(UserInfoURI).get()
            .uri(uriBuilder -> uriBuilder
                .build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (response) -> Mono.error(new RuntimeException(response.toString())))
            .bodyToMono(Map.class)
            .block();

        //System.out.println(response1);
        return response1;
    }
}
