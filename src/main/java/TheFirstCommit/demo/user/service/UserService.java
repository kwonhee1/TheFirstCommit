package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.dto.UserDeletePageDto;
import TheFirstCommit.demo.user.dto.request.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.request.UpdateUserFamilyDto;
import TheFirstCommit.demo.user.dto.response.ResponseTokenDto;
import TheFirstCommit.demo.user.social.RegisterDto;
import TheFirstCommit.demo.user.entity.UserEntity;

public interface UserService {

    // login
    ResponseTokenDto login(String socialId, RegisterDto registerDto);

    // update
    void update(UserEntity user, RequestUpdateUserInfoDto dto);

    // delete
    void delete(UserEntity user, Long nextLeaderUserId, boolean deleteUser);

    UserDeletePageDto getDeleteDto(UserEntity user);

}
