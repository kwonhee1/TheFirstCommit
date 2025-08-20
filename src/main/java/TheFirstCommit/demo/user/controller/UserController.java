package TheFirstCommit.demo.user.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentSummeryDto;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.dto.request.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.response.MyPageDto;
import TheFirstCommit.demo.user.dto.response.ResponseUserInfoDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PaymentService paymentService;

    @PatchMapping
    public ResponseEntity updateUserInfo(@AuthenticationPrincipal UserEntity user, @ModelAttribute RequestUpdateUserInfoDto dto) {
        log.info(dto.toString());
        userService.update(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping
    public ResponseEntity getUserInfo(@AuthenticationPrincipal UserEntity user) {
        ResponseUserInfoDto userInfoDto = ResponseUserInfoDto.of(user);
        Object paymentDto = paymentService.getFamilyPaymentDto(user);

        return ResponseEntity.ok().body(new SuccessResponse("success", MyPageDto.builder().paymentDto(paymentDto).userInfoDto(userInfoDto).build()));
    }

}
