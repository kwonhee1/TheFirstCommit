package TheFirstCommit.demo.payment.dto.response;

import TheFirstCommit.demo.family.PaymentDay;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponsePaymentInfoDto {

    private boolean hasCard;
    private PaymentDay paymentDay;

    public static ResponsePaymentInfoDto fromPaymentDay(boolean hasCard, PaymentDay paymentDay) {
        ResponsePaymentInfoDto dto = new ResponsePaymentInfoDto();
        dto.hasCard = hasCard;
        dto.paymentDay = paymentDay;
        return dto;
    }
}
