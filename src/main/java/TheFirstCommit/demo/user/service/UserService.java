package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.user.dto.request.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.request.UpdateUserFamilyDto;
import TheFirstCommit.demo.user.social.RegisterDto;
import TheFirstCommit.demo.user.entity.UserEntity;

public interface UserService {

    // login
    UserEntity login(String socialId, RegisterDto registerDto);

    // update
    void update(UserEntity user, RequestUpdateUserInfoDto dto);

    // delete
    void delete(UserEntity user, Long nextLeaderUserId);

}
