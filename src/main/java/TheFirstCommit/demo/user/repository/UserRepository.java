package TheFirstCommit.demo.user.repository;

import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findBySocialId(String socialId);

    @Query("select u from UserEntity u where u.family.id = :familyId and u.isLeader is true")
    Optional<UserEntity> findLeader(@Param("familyId") Long familyId);

}
