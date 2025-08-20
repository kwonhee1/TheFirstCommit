package TheFirstCommit.demo.family.dto.page;

import TheFirstCommit.demo.family.dto.response.ResponseElderDto;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyMemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Builder
@AllArgsConstructor
public class FamilyPageDto {

    private ResponseElderDto elder;
    private ResponseFamilyMemberDto member;

}
