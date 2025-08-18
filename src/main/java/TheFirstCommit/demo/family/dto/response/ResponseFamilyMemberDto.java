package TheFirstCommit.demo.family.dto.response;

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
    private ResponseElderDto elder;

    public static ResponseFamilyMemberDto of(UserEntity leader, List<UserEntity> members, ElderEntity elder) {
        ResponseFamilyMemberDto dto = new ResponseFamilyMemberDto();
        dto.leader = ResponseUserProfileDto.of(leader);
        dto.members = members.stream().map(ResponseUserProfileDto::of).collect(Collectors.toList());
        dto.memberCount = members.size();
        dto.elder = ResponseElderDto.of(elder);
        return dto;
    }

}
