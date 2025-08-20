package TheFirstCommit.demo.family.dto.response;

import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.img.ImgDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ResponseElderDto {

    private String name;
    private String birth;
    private String number;
    private String address;
    private String addressDetail;
    private String addressNumber;
    private ImgDto img;

    public static ResponseElderDto of(ElderEntity entity) {
        ResponseElderDto dto = new ResponseElderDto();
        dto.setName(entity.getName());
        dto.setBirth(entity.getBirth());
        dto.setNumber(entity.getNumber());
        dto.setAddress(entity.getAddress());
        dto.setAddressDetail(entity.getAddressDetail());
        dto.setAddressNumber(entity.getAddressNumber());
        dto.setImg(ImgDto.of(entity.getImg()));
        return dto;
    }
}
