package TheFirstCommit.demo.payment.dto;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter @Getter
@ToString
public class CardInfoDto {

    private String cardCompany;
    private String cardNumber;
    private String customerKey;
    private String billingKey;

    public CardInfoDto(Map response) {
        cardCompany = (String) response.get("cardCompany");
        cardNumber = (String) response.get("cardNumber");
        customerKey = (String) response.get("customerKey");
        billingKey = (String) response.get("billingKey");
    }

}
