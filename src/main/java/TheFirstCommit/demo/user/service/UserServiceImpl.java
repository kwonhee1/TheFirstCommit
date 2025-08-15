package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.family.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.img.ImgServiceImpl;
import TheFirstCommit.demo.user.dto.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.UpdateUserFamilyDto;
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

        userRepository.save(user);
    }

    public void updateUserFamily(UserEntity user, UpdateUserFamilyDto dto) {
        user.update(dto);
        userRepository.save(user);
    }

    @Override
    public void delete(UserEntity user) {
        // not yet
    }
}
