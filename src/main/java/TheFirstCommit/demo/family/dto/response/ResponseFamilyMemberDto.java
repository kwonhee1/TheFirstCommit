package TheFirstCommit.demo.family.dto.response;

import TheFirstCommit.demo.family.dto.FamilyMemberDto;
import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.user.dto.response.ResponseUserProfileDto;
import TheFirstCommit.demo.user.entity.UserEntity;
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

    public static ResponseFamilyMemberDto of(FamilyMemberDto dto) {
        ResponseFamilyMemberDto response = new ResponseFamilyMemberDto();
        response.leader = ResponseUserProfileDto.of(dto.getLeader());
        response.members = dto.getMember().stream().map(ResponseUserProfileDto::of).collect(Collectors.toList());
        response.memberCount = dto.getMember().size();
        return response;
    }

}
