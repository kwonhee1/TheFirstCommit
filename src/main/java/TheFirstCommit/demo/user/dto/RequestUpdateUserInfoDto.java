package TheFirstCommit.demo.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Setter @Getter
@ToString
public class RequestUpdateUserInfoDto {

    private String name;
    private String number;
    private String relation;
    private String birth;

    private MultipartFile img;

}
