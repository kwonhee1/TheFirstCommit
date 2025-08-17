package TheFirstCommit.demo.family.dto.request;

import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter @Setter
public class RequestNewFamilyDto {

    private String familyName;
    private PaymentDay paymentDay;

    private String relation;

    public FamilyEntity toFamilyEntity() {
        return FamilyEntity.builder()
            .familyName(familyName)
            .paymentDay(paymentDay)
            .build();
    }

}
