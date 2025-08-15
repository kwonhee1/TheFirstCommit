package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.family.FamilyEntity;
import TheFirstCommit.demo.user.dto.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.social.RegisterDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    // login
    UserEntity login(String socialId, RegisterDto registerDto);

    // update
    void update(UserEntity user, RequestUpdateUserInfoDto dto);

    // delete
    void delete(UserEntity user);

}
