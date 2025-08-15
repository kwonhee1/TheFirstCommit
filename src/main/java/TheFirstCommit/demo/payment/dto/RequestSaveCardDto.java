package TheFirstCommit.demo.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RequestSaveCardDto {

    private String customerKey;
    private String authKey;

}
