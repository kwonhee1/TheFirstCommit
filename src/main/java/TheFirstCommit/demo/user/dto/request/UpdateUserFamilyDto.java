package TheFirstCommit.demo.user.dto.request;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter @Setter
public class UpdateUserFamilyDto {
    private FamilyEntity family;
    private String relation;
    private boolean isLeader;
}
