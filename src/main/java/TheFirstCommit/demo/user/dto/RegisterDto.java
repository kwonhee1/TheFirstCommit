package TheFirstCommit.demo.user.dto;

import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RegisterDto {

    private String name;
    private String provider;
    private String socialId;
    private String imgURL;

    public UserEntity toEntity(ImgEntity img) {
        return UserEntity.builder()
            .name(name)
            .provider(provider)
            .socialId(socialId)
            .img(img)
            .build();
    }
}
