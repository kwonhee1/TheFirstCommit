package TheFirstCommit.demo.user.dto;

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

    public static ResponseTokenDto access(String accessToken) { return new ResponseTokenDto(accessToken, null); }
    public static ResponseTokenDto refresh(String refreshToken) { return new ResponseTokenDto(null, refreshToken); }
    public static ResponseTokenDto all(String accessToken, String refreshToken) { return new ResponseTokenDto(accessToken, refreshToken); }
}
