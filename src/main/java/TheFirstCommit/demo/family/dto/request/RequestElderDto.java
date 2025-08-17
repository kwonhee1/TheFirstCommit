package TheFirstCommit.demo.family.dto.request;

import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RequestElderDto {

    private String name;
    private String birth;
    private String number;
    private String address;
    private String addressDetail;
    private String addressNumber;

    public ElderEntity toEntity(FamilyEntity family, ImgEntity img) {
        return ElderEntity.builder()
            .family(family)
            .name(name)
            .birth(birth)
            .number(number)
            .address(address)
            .addressDetail(addressDetail)
            .addressNumber(addressNumber)
            .img(img)
            .build();
    }

}
