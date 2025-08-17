package TheFirstCommit.demo.family.repository;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<FamilyEntity,Long> {

}
