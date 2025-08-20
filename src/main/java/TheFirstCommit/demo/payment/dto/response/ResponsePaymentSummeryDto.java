package TheFirstCommit.demo.payment.dto.response;

import TheFirstCommit.demo.family.PaymentDay;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponsePaymentSummeryDto {

    private boolean hasCard;
    private PaymentDay paymentDay;

    public static ResponsePaymentSummeryDto of(boolean hasCard, PaymentDay paymentDay) {
        ResponsePaymentSummeryDto dto = new ResponsePaymentSummeryDto();
        dto.hasCard = hasCard;
        dto.paymentDay = paymentDay;
        return dto;
    }
}
