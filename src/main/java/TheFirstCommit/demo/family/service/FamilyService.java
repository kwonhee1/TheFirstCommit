package TheFirstCommit.demo.family.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.repository.FamilyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;

    public List<FamilyEntity> findAll() {
        return familyRepository.findAll();
    }
}
