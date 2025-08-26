package TheFirstCommit.demo.user.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.config.security.util.JWTUtil;
import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.user.dto.response.ResponseTokenDto;
import TheFirstCommit.demo.user.service.UserValidateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/token")
@Slf4j
public class TokenController {

    private final UserValidateService userValidateService;

    @PostMapping
    public ResponseEntity refreshAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.NOT_FOUND, "refresh token");
        }
        String token = authorizationHeader.substring(7);

        Long userId = JWTUtil.decodeToken(token);
        userValidateService.findById(userId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_TOKEN)
        );
        log.info("refresh access token success " + userId);
        return ResponseEntity.ok(new SuccessResponse("success", new ResponseTokenDto(JWTUtil.generateAccessToken(userId))));
    }
}
