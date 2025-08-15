package TheFirstCommit.demo.user.social;

import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Builder
@AllArgsConstructor
public class RegisterDto {

    private String name;
    private String number;
    private String birth;
    private String provider;
    private String socialId;
    private String imgURL;

    public UserEntity toEntity(ImgEntity img) {
        return UserEntity.builder()
            .name(name)
            .number(number)
            .birth(birth)
            .provider(provider)
            .socialId(socialId)
            .img(img)
            .build();
    }
}
