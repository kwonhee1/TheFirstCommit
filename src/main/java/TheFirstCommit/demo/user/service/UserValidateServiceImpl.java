package TheFirstCommit.demo.user.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidateServiceImpl implements UserValidateService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public FamilyEntity getFamily(UserEntity user) {
        return userRepository.getFamily(user.getId()).orElseThrow(
            ()->{return new CustomException(ErrorCode.NOT_FOUND, "family");}
        );
    }

    @Override
    public UserEntity findLeader(UserEntity user){
        return userRepository.findLeader(user.getId()).orElseThrow(
            ()->{return new CustomException(ErrorCode.NOT_FOUND, "leader");}
        );
    }
}
