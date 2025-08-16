package TheFirstCommit.demo.payment.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.payment.dto.RequestSaveCardDto;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/card")
    public ResponseEntity saveNewCard(@AuthenticationPrincipal UserEntity user, RequestSaveCardDto dto) {
        paymentService.saveCard(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/test")
    public ResponseEntity testPayment(@AuthenticationPrincipal UserEntity user) {
        if(paymentService.payment(user.getCard()))
            return ResponseEntity.ok().body(new SuccessResponse("success", null));
        return ResponseEntity.badRequest().body(new SuccessResponse("fail", null));
    }
}
