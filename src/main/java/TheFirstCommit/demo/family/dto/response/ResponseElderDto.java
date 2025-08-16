package TheFirstCommit.demo.family.dto.response;

import TheFirstCommit.demo.family.entity.ElderEntity;
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
    private String imgPid;

    public static ResponseElderDto of(ElderEntity entity) {
        return null; // not yet!
    }
}
