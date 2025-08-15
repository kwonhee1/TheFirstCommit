package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.Optional;

public interface UserValidateService {

    Optional<UserEntity> findById(Long userId);

}
