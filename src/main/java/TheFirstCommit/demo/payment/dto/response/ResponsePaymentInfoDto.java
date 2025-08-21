package TheFirstCommit.demo.payment.dto.response;

import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.dto.response.ResponseUserProfileDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.time.LocalDate;
import java.time.YearMonth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponsePaymentInfoDto {

    private PaymentDay feedEndDay;
    private LocalDate nextPaymentDay;
    private LocalDate sincePaymentDay;
    private ResponseUserProfileDto leader;

    public static ResponsePaymentInfoDto of(UserEntity leader, FamilyEntity family) {
        ResponsePaymentInfoDto dto = new ResponsePaymentInfoDto();
        dto.leader = ResponseUserProfileDto.of(leader);
        dto.feedEndDay = family.getPaymentDay();
        dto.nextPaymentDay = family.getNextPayDate();
        dto.sincePaymentDay = family.getSincePayDay();
        return dto;
    }

}
