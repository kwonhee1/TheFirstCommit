package TheFirstCommit.demo.user.dto.response;

import TheFirstCommit.demo.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class ResponseTokenDto {

    private String accessToken;
    private String refreshToken;
    @JsonProperty("user")
    private ResponseUserDetailDto userDetailDto;
    private boolean isFirst;

    @JsonIgnore
    private UserEntity userEntity;

    public ResponseTokenDto(UserEntity userEntity) {
        this.userEntity = userEntity;
        isFirst = false;
    }
    public ResponseTokenDto(UserEntity userEntity, ResponseUserDetailDto userDetailDto) {
        this.userEntity = userEntity;
        this.userDetailDto = userDetailDto;
        isFirst = true;
    }
    public ResponseTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
