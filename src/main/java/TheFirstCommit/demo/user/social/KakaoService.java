package TheFirstCommit.demo.user.social;

import TheFirstCommit.demo.user.dto.response.ResponseTokenDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import io.netty.handler.codec.http.HttpHeaderValues;
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
public class KakaoService {

    private final UserService userService;

    @Value("${social.kakao.token-uri}")
    private String AccessTokenURI;
    @Value("${social.kakao.user-info-uri}")
    private String UserInfoURI;

    @Value("${kakao.client-id}")
    private String ClientId;
    @Value("${kakao.client-secret}")
    private String ClientSecret;

    public ResponseTokenDto socialLogin(String code) {
        Map<String, Object> info = getUserFromKakao(code);

        return userService.login(String.valueOf(info.get("id")), RegisterDto
            .builder()
            .provider("kakao")
            .socialId( String.valueOf(info.get("id")))
            //.nickName( (String) ((Map<String, Object>)info.get("properties")).get("nickname") )
            .name((String) ((Map<String, Object>)info.get("kakao_account")).get("name"))
            .number((String) ((Map<String, Object>)info.get("kakao_account")).get("phone"))
            // .birth()
            //.email( (String) ((Map<String, Object>)info.get("kakao_account")).get("email") )
            .imgURL((String) ((Map<String, Object>)info.get("properties")).get("profile_image"))
            .build()
        );
    }

    public Map<String, Object> getUserFromKakao(String code) {
        Map<String, Object> kakaoToken = WebClient.create(AccessTokenURI).post()
            .uri(uriBuilder -> uriBuilder
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", ClientId)
                .queryParam("code", code)
                .queryParam("client_secret", ClientSecret)
                .build(true))
            .header(HttpHeaders.CONTENT_TYPE,
                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
            .bodyToMono(Map.class)
            .block();

        return getUserInfo( (String) kakaoToken.get("access_token"));
    }

    public Map<String, Object> getUserInfo(String accessToken) {

        Map response = WebClient.create(UserInfoURI)
            .get()
            .uri(uriBuilder -> uriBuilder
                .build(true))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
            .bodyToMono(Map.class)
            .block();

        System.out.println(response);
        return response;
    }
}
