package TheFirstCommit.demo.user.service;

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
}
