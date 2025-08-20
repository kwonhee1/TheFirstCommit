package TheFirstCommit.demo.user.dto.response;

import TheFirstCommit.demo.img.ImgDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ResponseUserProfileDto {

    private Long id;
    private String name;
    private String relation;
    private ImgDto img;

    public static ResponseUserProfileDto of(UserEntity userEntity) {
        ResponseUserProfileDto responseUserProfileDto = new ResponseUserProfileDto();
        responseUserProfileDto.name = userEntity.getName();
        responseUserProfileDto.relation = userEntity.getRelation();
        responseUserProfileDto.id = userEntity.getId();
        responseUserProfileDto.img = userEntity.getImg()!=null ? ImgDto.of(userEntity.getImg()) : null;
        return responseUserProfileDto;
    }
}
