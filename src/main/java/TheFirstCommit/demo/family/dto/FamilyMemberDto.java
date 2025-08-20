package TheFirstCommit.demo.family.dto;

import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FamilyMemberDto {

    private String familyName;
    private List<UserEntity> member;
    private UserEntity leader;

    public static FamilyMemberDto of(String familyName, List<UserEntity> member, UserEntity leader) {
        FamilyMemberDto dto = new FamilyMemberDto();
        dto.familyName = familyName;
        dto.member = member;
        dto.leader = leader;
        return dto;
    }

}
