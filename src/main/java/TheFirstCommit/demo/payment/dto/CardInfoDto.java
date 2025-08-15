package TheFirstCommit.demo.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Builder
@AllArgsConstructor
public class CardInfoDto {
    private String customerKey;
    private String billingKey;
}
