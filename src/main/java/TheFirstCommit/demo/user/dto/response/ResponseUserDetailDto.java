package TheFirstCommit.demo.user.dto.response;

import TheFirstCommit.demo.img.ImgDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseUserDetailDto {

    private Long id;
    private String name;
    private String number;
    private String birth;
    private String socialProvider;
    private String relation;
    @JsonProperty("isLeader")
    private boolean isLeader;
    private ImgDto img;

    public static ResponseUserDetailDto of(UserEntity user) {
        ResponseUserDetailDto dto = new ResponseUserDetailDto();
        dto.id = user.getId();
        dto.name = user.getName();
        dto.number = user.getNumber();
        dto.birth = user.getBirth();
        dto.isLeader = user.isLeader();
        dto.img = ImgDto.of(user.getImg());
        dto.relation = user.getRelation();
        dto.socialProvider = user.getProvider();
        return dto;
    }

}
