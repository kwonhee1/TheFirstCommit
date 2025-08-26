package TheFirstCommit.demo.family.dto.response;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ResponseFamilyDto {

    private String familyName;
    private long memberCount;

    public static ResponseFamilyDto of(String familyName, long memberCount) {
        ResponseFamilyDto dto = new ResponseFamilyDto();
        dto.familyName = familyName;
        dto.memberCount = memberCount;
        return dto;
    }
}
