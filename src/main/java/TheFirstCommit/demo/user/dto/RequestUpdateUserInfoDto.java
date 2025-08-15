package TheFirstCommit.demo.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class RequestUpdateUserInfoDto {

    private String name;
    private String number;
    private String relation;

}
