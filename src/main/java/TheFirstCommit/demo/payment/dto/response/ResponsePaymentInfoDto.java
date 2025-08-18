package TheFirstCommit.demo.payment.dto.response;

import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.user.dto.response.ResponseUserProfileDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponsePaymentInfoDto {

    private PaymentDay paymentDay;
    private LocalDate nextPaymentDay;
    private LocalDate sincePaymentDay;
    private ResponseUserProfileDto leader;

    public static ResponsePaymentInfoDto of(PaymentDay paymentDay, UserEntity leader) {
        ResponsePaymentInfoDto dto = new ResponsePaymentInfoDto();
        dto.leader = ResponseUserProfileDto.of(leader);
        dto.paymentDay = paymentDay;
        dto.nextPaymentDay = paymentDay.getDay();
        dto.sincePaymentDay = leader.getCreatedAt().toLocalDate();
        return dto;
    }

}
