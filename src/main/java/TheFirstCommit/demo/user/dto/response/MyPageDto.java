package TheFirstCommit.demo.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Builder
@AllArgsConstructor
public class MyPageDto {

    private ResponseUserInfoDto userInfoDto;
    private Object paymentDto;

}
