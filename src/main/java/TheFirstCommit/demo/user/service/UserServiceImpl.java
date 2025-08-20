package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyDto;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.service.FamilyService;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.user.dto.request.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.request.UpdateUserFamilyDto;
import TheFirstCommit.demo.user.social.RegisterDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.repository.UserRepository;
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

    @Override
    public UserEntity login(String socialId, RegisterDto dto) {
        UserEntity user = userRepository.findBySocialId(socialId).orElseGet(
            ()->register(dto)
        );
        log.info(String.format("login success %s %s", user.getId(), user.getName()));
        return user;
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

        log.info(user.getImg().toString());

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(UserEntity user, Long nextLeaderUserId) {
        if(nextLeaderUserId == null) {
            deleteAll(user);
            return;
        }

        UserEntity nextLeader = userRepository.findById(nextLeaderUserId).orElseThrow();
        nextLeader.updateLeader();
        userValidateService.getFamily(user).setIsChanged(true);

        userRepository.delete(user);
        userRepository.save(nextLeader);
    }

    @Transactional
    public void deleteAll(UserEntity user) {
        ResponseFamilyDto dto = familyService.getFamilyDto(user);
        if(dto.getMemberCount() >= 2)
            throw new CustomException(ErrorCode.CHOOSE_NEXT_LEADER);

        familyService.deleteFamily(userValidateService.getFamily(user));
        userRepository.delete(user);
    }
}
