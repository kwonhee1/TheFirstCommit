package TheFirstCommit.demo.user.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentSummeryDto;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.dto.request.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.response.MyPageDto;
import TheFirstCommit.demo.user.dto.response.ResponseUserDetailDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PaymentService paymentService;

    @PatchMapping("/public/user")
    public ResponseEntity updateUserInfo(@AuthenticationPrincipal UserEntity user, @ModelAttribute RequestUpdateUserInfoDto dto) {
        if(user == null)
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        userService.update(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/api/user")
    public ResponseEntity getUserInfo(@AuthenticationPrincipal UserEntity user) {
        if(user == null)
            throw new CustomException(ErrorCode.UNAUTHORIZED);

        ResponseUserDetailDto userInfoDto = ResponseUserDetailDto.of(user);
        Object paymentDto = paymentService.getFamilyPaymentDto(user);

        return ResponseEntity.ok().body(new SuccessResponse("success", MyPageDto.builder().paymentDto(paymentDto).userInfoDto(userInfoDto).build()));
    }

    @PostMapping("/api/user/delete")
    public ResponseEntity deleteUser(@AuthenticationPrincipal UserEntity user, @RequestBody(required = false) Map<String, Long> request) {
        userService.delete(user, request != null ? request.get("nextLeaderId"):null, true);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/api/user/delete")
    public ResponseEntity deleteUser(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok().body(new SuccessResponse("success",userService.getDeleteDto(user)));
    }

}
