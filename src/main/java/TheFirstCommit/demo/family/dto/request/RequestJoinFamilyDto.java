package TheFirstCommit.demo.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class RequestJoinFamilyDto {

    private String familyCode;
    private String relation;

}
