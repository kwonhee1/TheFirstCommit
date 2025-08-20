package TheFirstCommit.demo.user.dto.response;

import TheFirstCommit.demo.payment.dto.response.ResponsePaymentInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class MyPageDto {

    private ResponseUserInfoDto userInfoDto;
    private Object paymentDto;
    @JsonProperty("hasCard")
    private boolean hasCard;

    @Builder
    public MyPageDto(ResponseUserInfoDto userInfoDto, Object paymentDto) {
        this.userInfoDto = userInfoDto;
        this.paymentDto = paymentDto;
        this.hasCard = paymentDto instanceof ResponsePaymentInfoDto;
    }
}
