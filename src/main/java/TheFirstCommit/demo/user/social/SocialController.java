package TheFirstCommit.demo.user.social;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.config.security.util.JWTUtil;
import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.user.dto.response.ResponseTokenDto;
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

@RequestMapping("/public/social")
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

    @GetMapping("/{provider}")
    public void social(@PathVariable("provider") String provider, HttpServletResponse response) throws IOException {
        String authorizationUri, clientId, scope = "";
        switch (provider) {
            case "google":
                authorizationUri = googleAuthorizationUri;
                clientId = googleClientId;
                scope = "&scope=openid%20profile";
                break;
            case "kakao":
                authorizationUri = kakaoAuthorizationUri;
                clientId = kakaoClientId;
                // scope = "&scope=profile_image%20name%20birthday%20birthyear%20phone_number";
                break;
            case "naver":
                authorizationUri = naverAuthorizationUri;
                clientId = naverClientId;
                scope = "&scope=nickname%20profile_image%20birthday%20mobile";
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

    @PostMapping("/google")
    public ResponseEntity google(@RequestBody SocialDto dto) {
        ResponseTokenDto tokenDto = googleService.socialLogin(dto.getCode());
        return response(tokenDto);
    }

    @PostMapping("/kakao")
    public ResponseEntity kakao(@RequestBody SocialDto dto) {
        ResponseTokenDto tokenDto = kakaoService.socialLogin(dto.getCode());
        return response(tokenDto);
    }

    @PostMapping("/naver")
    public ResponseEntity naver(@RequestBody SocialDto dto) {
        ResponseTokenDto tokenDto = naverService.socialLoin(dto.getCode());
        return response(tokenDto);
    }

    private ResponseEntity response(ResponseTokenDto tokenDto) {
        String access = JWTUtil.generateAccessToken(tokenDto.getUserEntity().getId());
        String refresh = JWTUtil.generateRefreshToken(tokenDto.getUserEntity().getId());

        tokenDto.setAccessToken(access);
        tokenDto.setRefreshToken(refresh);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", tokenDto));
    }
}
