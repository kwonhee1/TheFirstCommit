package TheFirstCommit.demo.user.dto.response;

import TheFirstCommit.demo.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseUserInfoDto {

    private String name;
    @JsonProperty("isLeader")
    private boolean isLeader;
    private String socialProvider;

    public static ResponseUserInfoDto of(UserEntity userEntity) {
        ResponseUserInfoDto responseUserInfoDto = new ResponseUserInfoDto();
        responseUserInfoDto.name = userEntity.getName();
        responseUserInfoDto.isLeader = userEntity.isLeader();
        responseUserInfoDto.socialProvider = userEntity.getProvider();
        return responseUserInfoDto;
    }
}
