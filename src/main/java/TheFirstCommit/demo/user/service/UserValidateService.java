package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.dto.request.UpdateUserFamilyDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.Optional;

public interface UserValidateService {

    Optional<UserEntity> findById(Long userId);
    FamilyEntity getFamily(UserEntity user); // thorws Not_Found "family"
    UserEntity findLeader(UserEntity user);
    void updateUserFamily(UserEntity user, UpdateUserFamilyDto dto);
}
