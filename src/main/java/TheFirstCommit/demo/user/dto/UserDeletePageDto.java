package TheFirstCommit.demo.user.dto;

import TheFirstCommit.demo.family.dto.response.ResponseFamilyMemberDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class UserDeletePageDto {

    @JsonProperty("isLeader")
    private boolean isLeader;
    private ResponseFamilyMemberDto familyMember;

}
