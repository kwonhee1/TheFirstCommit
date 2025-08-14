package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.family.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.img.ImgServiceImpl;
import TheFirstCommit.demo.user.dto.RegisterDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImgServiceImpl imgServiceImpl;

    @Override
    public Optional<UserEntity> login(String socialId) {
        return userRepository.findBySocialId(socialId);
    }

    @Override
    public UserEntity register(RegisterDto dto) {
        ImgEntity img = imgServiceImpl.getImg(dto.getImgURL());
        return userRepository.save(dto.toEntity(img));
    }

    @Override
    @Transactional
    public void update(UserEntity user, MultipartFile imgFile) {
        imgServiceImpl.update(user.getImg(), imgFile);
        // user.img_id :: img 의 cid값만 변경하면 됨 => img entity 자체를 새로 만들 피요 없음
    }

    @Override
    public void update(UserEntity user, String name, String relation) {
        if(name!= null && name.isEmpty())
            user.updateName(name);
        if(relation != null && relation.isEmpty())
            user.updateRelation(relation);
        userRepository.save(user);
    }

    @Override
    public void delete(UserEntity user) {
        // not yet
    }

    @Override
    public void setFamily(UserEntity user, FamilyEntity family, boolean isLeader) {
        user.updateFamily(family);
        user.updateIsLeader(isLeader); // set role = USER
        userRepository.save(user);
    }
}
