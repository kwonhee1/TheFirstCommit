package TheFirstCommit.demo.payment.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.payment.dto.CardInfoDto;
import TheFirstCommit.demo.payment.dto.RequestSaveCardDto;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/public/payment/card")
    public ResponseEntity saveNewCard(@AuthenticationPrincipal UserEntity user,@RequestBody RequestSaveCardDto dto) {
        if(user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        if(user.getCard() != null)
            throw new CustomException(ErrorCode.ALREADY_EXIST, "card");

        CardInfoDto cardInfo = paymentService.saveCard(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", cardInfo.toResponseDto()));
    }

    @GetMapping("api/payment/test")
    public ResponseEntity testPayment(@AuthenticationPrincipal UserEntity user) {
        if(paymentService.payment(user.getCard()))
            return ResponseEntity.ok().body(new SuccessResponse("success", null));
        return ResponseEntity.badRequest().body(new SuccessResponse("fail", null));
    }
}
