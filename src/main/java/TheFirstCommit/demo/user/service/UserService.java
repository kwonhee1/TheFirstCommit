package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.family.FamilyEntity;
import TheFirstCommit.demo.user.dto.RegisterDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    // login
    Optional<UserEntity> login(String socialId);

    // register
    UserEntity register(RegisterDto dto);

    // update
    void update(UserEntity user, MultipartFile imgFile);
    void update(UserEntity user, String name, String relation);

    // delete
    void delete(UserEntity user);

    // set family (make new family or set other exist family)
    void setFamily(UserEntity user, FamilyEntity family, boolean isLeader);

}
