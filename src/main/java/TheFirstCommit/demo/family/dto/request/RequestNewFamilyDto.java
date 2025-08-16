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

    private RequestElderDto elder;

    private String relation;

    public ElderEntity toElderEntity(FamilyEntity familyEntity, ImgEntity imgEntity) {
        return elder.toEntity(familyEntity, imgEntity);
    }

    public FamilyEntity toFamilyEntity() {
        return FamilyEntity.builder()
            .familyName(familyName)
            .paymentDay(paymentDay)
            .build();
    }

}
