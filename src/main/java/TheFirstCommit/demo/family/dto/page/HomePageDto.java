package TheFirstCommit.demo.family.dto.page;

import TheFirstCommit.demo.family.dto.response.ResponseFamilyDto;
import TheFirstCommit.demo.feed.dto.ResponseFeedDto;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentSummeryDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class HomePageDto {

    private ResponsePaymentSummeryDto payment;
    private ResponseFamilyDto family;
    private List<ResponseFeedDto> feed;

    public HomePageDto(ResponsePaymentSummeryDto payment, ResponseFamilyDto family , List<ResponseFeedDto> feed) {
        this.payment = payment;
        this.family = family;
        this.feed = feed;
    }

}
