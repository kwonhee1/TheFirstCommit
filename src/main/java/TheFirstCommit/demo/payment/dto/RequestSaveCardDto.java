package TheFirstCommit.demo.payment.dto;

import TheFirstCommit.demo.family.PaymentDay;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class RequestSaveCardDto {

    @NotEmpty @NotNull
    private String customerKey;
    @NotEmpty @NotNull
    private String authKey;

    private PaymentDay paymentDay;

}
