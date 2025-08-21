package TheFirstCommit.demo.family.dto.response;

import TheFirstCommit.demo.family.dto.FamilyMemberDto;
import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.user.dto.response.ResponseUserProfileDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class ResponseFamilyMemberDto {

    private ResponseUserProfileDto leader;
    private List<ResponseUserProfileDto> members;
    private long memberCount;
    @JsonProperty("isLeader")
    private boolean isLeader;

    public static ResponseFamilyMemberDto of(FamilyMemberDto dto, boolean isLeader) {
        ResponseFamilyMemberDto response = new ResponseFamilyMemberDto();
        response.leader = ResponseUserProfileDto.of(dto.getLeader());
        response.members = dto.getMember().stream().map(ResponseUserProfileDto::of).collect(Collectors.toList());
        response.memberCount = dto.getMember().size() + 1; // leader 한명 포함
        response.isLeader = isLeader;
        return response;
    }

}
