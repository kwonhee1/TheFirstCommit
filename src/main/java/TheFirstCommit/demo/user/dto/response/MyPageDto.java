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

    private ResponseUserDetailDto userInfoDto;
    private Object paymentDto;
    @JsonProperty("hasPaid")
    private boolean isPaid;

    @Builder
    public MyPageDto(ResponseUserDetailDto userInfoDto, Object paymentDto) {
        this.userInfoDto = userInfoDto;
        this.paymentDto = paymentDto;
        this.isPaid = paymentDto instanceof ResponsePaymentInfoDto;
    }
}
