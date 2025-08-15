package TheFirstCommit.demo.user.social;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.config.security.util.JWTUtil;
import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.user.dto.ResponseTokenDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/public/social/")
@Controller
@Slf4j
@RequiredArgsConstructor
public class SocialController {

    private final GoogleService googleService;
    private final KakaoService kakaoService;
    private final NaverService naverService;

    @Value("${social.google.authorization-uri}")
    private String googleAuthorizationUri;
    @Value("${social.naver.authorization-uri}")
    private String naverAuthorizationUri;
    @Value("${social.kakao.authorization-uri}")
    private String kakaoAuthorizationUri;

    @Value("${google.client-id}")
    private String googleClientId;
    @Value("${naver.client-id}")
    private String naverClientId;
    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${FRONT_DOMAIN}")
    private String domain;

    @GetMapping("{provider}")
    public void social(@PathVariable("provider") String provider, HttpServletResponse response) throws IOException {
        String authorizationUri, clientId, scope = "";
        switch (provider) {
            case "google":
                authorizationUri = googleAuthorizationUri;
                clientId = googleClientId;
                scope = "&scope=openid";
                break;
            case "kakao":
                authorizationUri = kakaoAuthorizationUri;
                clientId = kakaoClientId;
                break;
            case "naver":
                authorizationUri = naverAuthorizationUri;
                clientId = naverClientId;
                break;
            default:
                throw new CustomException(ErrorCode.NOT_FOUND, "provider");
        }
        response.sendRedirect(
            String.format(
                "%s?response_type=code&client_id=%s&redirect_uri=%s/social/%s%s" ,
                authorizationUri ,clientId, domain, provider, scope
            )
        );
    }

    @PostMapping("google")
    public ResponseEntity google(@RequestBody SocialDto dto) {
        UserEntity user = googleService.socialLogin(dto.getCode());
        return response(user.getId());
    }

    @PostMapping("kakao")
    public ResponseEntity kakao(@RequestBody SocialDto dto) {
        UserEntity user = kakaoService.socialLogin(dto.getCode());
        return response(user.getId());
    }

    @PostMapping("naver")
    public ResponseEntity naver(@RequestBody SocialDto dto) {
        UserEntity user = naverService.socialLoin(dto.getCode());
        return response(user.getId());
    }

    private ResponseEntity response(Long userId) {
        String access = JWTUtil.generateAccessToken(userId);
        String refresh = JWTUtil.generateRefreshToken(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", ResponseTokenDto.all(access, refresh) ));
    }
}
