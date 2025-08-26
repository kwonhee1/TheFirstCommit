package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyDto;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyMemberDto;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.service.FamilyPageDtoService;
import TheFirstCommit.demo.family.service.FamilyService;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.user.dto.UserDeletePageDto;
import TheFirstCommit.demo.user.dto.request.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.request.UpdateUserFamilyDto;
import TheFirstCommit.demo.user.dto.response.ResponseTokenDto;
import TheFirstCommit.demo.user.dto.response.ResponseUserDetailDto;
import TheFirstCommit.demo.user.social.RegisterDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.repository.UserRepository;
import TheFirstCommit.demo.user.social.SocialDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImgService imgService;
    private final UserValidateService userValidateService;
    private final FamilyService familyService;
    private final FamilyPageDtoService familyPageDtoService;

    @Override
    public ResponseTokenDto login(String socialId, RegisterDto dto) {
        Optional<UserEntity> user = userRepository.findBySocialId(socialId);

        if(user.isPresent())
            return new ResponseTokenDto(user.get());
        else {
            UserEntity newUser = register(dto);
            return new ResponseTokenDto(newUser, ResponseUserDetailDto.of(newUser));
        }
    }

    private UserEntity register(RegisterDto dto) {
        ImgEntity img = imgService.getImg(dto.getImgURL());
        return userRepository.save(dto.toEntity(img));
    }

    @Override
    @Transactional
    public void update(UserEntity user, RequestUpdateUserInfoDto dto) {
        if(dto.getImg() != null){
            if(user.getImg() != null)
                imgService.update(user.getImg(), dto.getImg());
            else
                user.update(imgService.save(dto.getImg()));
        }

        user.update(dto);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(UserEntity user, Long nextLeaderUserId, boolean deleteUser) {
        ResponseFamilyDto dto = familyService.getFamilyDto(user);
        FamilyEntity family = userValidateService.getFamily(user);
        Long memberCont = dto.getMemberCount();

        if(user.isLeader() && memberCont >= 2){
            if(nextLeaderUserId == null)
                throw new CustomException(ErrorCode.CHOOSE_NEXT_LEADER);
            UserEntity nextLeader = userRepository.findById(nextLeaderUserId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND, "next leader")
            );
            changeLeader(user, nextLeader, family);
        }
        else if(memberCont == 1 && deleteUser){
            userRepository.delete(user);
            familyService.deleteFamily(family);
            return;
        }

        if(deleteUser) {
            userRepository.delete(user);
        }
    }

    private void changeLeader(UserEntity user, UserEntity nextLeader, FamilyEntity family) {
        if(nextLeader.getFamily().getId() != family.getId())
            throw new CustomException(ErrorCode.NOT_FOUND, "next leader");
        nextLeader.updateLeader(true);
        user.updateLeader(false);
        family.setIsChanged(true);
        userRepository.save(user);
        userRepository.save(nextLeader);
    }

    public UserDeletePageDto getDeleteDto(UserEntity user) {
        ResponseFamilyMemberDto memberDto = familyPageDtoService.getFamilyPage(user).getMember();
        return new UserDeletePageDto(user.isLeader(), memberDto);
    }
}
