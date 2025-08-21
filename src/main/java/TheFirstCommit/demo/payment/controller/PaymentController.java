package TheFirstCommit.demo.payment.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.payment.dto.CardInfoDto;
import TheFirstCommit.demo.payment.dto.RequestSaveCardDto;
import TheFirstCommit.demo.payment.dto.response.ResponseCardInfoDto;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentService cardService;
    private final UserService userService;

    @PostMapping("/public/payment/card")
    public ResponseEntity saveNewCard(@AuthenticationPrincipal UserEntity user,@RequestBody RequestSaveCardDto dto) {
        if(user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

//        if(user.getCard() != null)
//            throw new CustomException(ErrorCode.ALREADY_EXIST, "card");

        CardInfoDto cardInfo = cardService.saveCard(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", ResponseCardInfoDto.of(cardInfo)));
    }

    @GetMapping("/api/payment/card")
    public ResponseEntity getCardInfo(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok().body(new SuccessResponse("success", cardService.getCardInfo(user)));
    }

    @DeleteMapping("/api/payment/card")
    public ResponseEntity deleteCard(@AuthenticationPrincipal UserEntity user, @RequestBody(required = false) Map<String, Long> request) {
        cardService.remove(user);
        userService.delete(user, request.get("nextLeaderId"), false);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/api/payment/card/test")
    public ResponseEntity testPayment(@AuthenticationPrincipal UserEntity user) {
        if(paymentService.testPayment(user))
            return ResponseEntity.ok().body(new SuccessResponse("success", null));
        return ResponseEntity.badRequest().body(new SuccessResponse("fail", null));
    }

    @GetMapping("/api/payment/test")
    public ResponseEntity testMonthlyPayment() {
        paymentService.MonthlyPayment();
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }
}
