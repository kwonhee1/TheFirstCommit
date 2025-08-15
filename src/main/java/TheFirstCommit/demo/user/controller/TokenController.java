package TheFirstCommit.demo.user.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.config.security.util.JWTUtil;
import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.user.dto.ResponseTokenDto;
import TheFirstCommit.demo.user.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/public/token/")
public class TokenController {

    private final UserValidateService userValidateService;

    @PostMapping
    public ResponseEntity refreshAccessToken(@RequestParam String refreshToken) {
        Long userId = JWTUtil.decodeToken(refreshToken);
        userValidateService.findById(userId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_TOKEN)
        );
        return ResponseEntity.ok(new SuccessResponse("success", ResponseTokenDto.access(JWTUtil.generateAccessToken(userId))));
    }
}
