package TheFirstCommit.demo.user.social;

import TheFirstCommit.demo.user.dto.response.ResponseTokenDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NaverService {

    private final UserService userService;

    @Value("${social.naver.token-uri}")
    private String AccessTokenURI;
    @Value("${social.naver.user-info-uri}")
    private String UserInfoURI;

    @Value("${naver.client-id}")
    private String ClientId;
    @Value("${naver.client-secret}")
    private String ClientSecret;

    public ResponseTokenDto socialLoin(String code) {
        Map<String, String> info = getUserFromNaver(code);

        return userService.login(info.get("id"), RegisterDto
            .builder()
            .provider("naver")
            .socialId(info.get("id"))
            //.email(info.get("email"))
            .name(info.get("name"))
            .number(info.get("mobile"))
            // .birth()
            //.nickName(info.get("nickname"))
            .imgURL(info.get("profile_image"))
            .build()
        );
    }

    public Map<String, String> getUserFromNaver(String code) {
        Map<String, Object> naverToken = WebClient.create(AccessTokenURI).post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", ClientId)
                .queryParam("client_secret", ClientSecret)
                .queryParam("code", code)
                // .queryParam("state", UUID.randomUUID().toString())
                .build(true))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(Map.class)
            .block();

        return getUserInfo( (String) naverToken.get("access_token"));
    }

    public Map<String, String> getUserInfo(String accessToken) {
        Map response1 = WebClient.create(UserInfoURI).post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("client_id", ClientId)
                .queryParam("client_secret", ClientSecret)
                .build(true))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(Map.class)
            .map(response -> {
                return (Map<String, String>) response.get("response");
            })
            .block();
        response1.values().forEach(System.out::println);
        return response1;
    }
}
