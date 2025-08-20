package TheFirstCommit.demo.user.dto.response;

import TheFirstCommit.demo.user.entity.UserEntity;
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
    private ResponseUserDetailDto user;

    public static ResponseTokenDto access(String accessToken) { return new ResponseTokenDto(accessToken, null, null); }
    // public static ResponseTokenDto refresh(String refreshToken) { return new ResponseTokenDto(null, refreshToken, null); }
    public static ResponseTokenDto all(String accessToken, String refreshToken, UserEntity user) { return new ResponseTokenDto(accessToken, refreshToken, ResponseUserDetailDto.of(user)); }
}
