package TheFirstCommit.demo.payment.dto.response;

import TheFirstCommit.demo.payment.dto.CardInfoDto;
import TheFirstCommit.demo.payment.entity.CardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ResponseCardInfoDto {
    private String cardCompany;
    private String cardNumber;

    public static ResponseCardInfoDto of(CardInfoDto cardInfoDto) {
        ResponseCardInfoDto responseCardInfoDto = new ResponseCardInfoDto();
        responseCardInfoDto.cardCompany = cardInfoDto.getCardCompany();
        responseCardInfoDto.cardNumber = cardInfoDto.getCardNumber();
        return responseCardInfoDto;
    }

    public static ResponseCardInfoDto of(CardEntity cardEntity) {
        ResponseCardInfoDto responseCardInfoDto = new ResponseCardInfoDto();
        responseCardInfoDto.cardCompany = cardEntity.getCardCompany();
        responseCardInfoDto.cardNumber = cardEntity.getCardNumber();
        return responseCardInfoDto;
    }
}
