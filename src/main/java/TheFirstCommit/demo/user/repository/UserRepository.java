package TheFirstCommit.demo.user.repository;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findBySocialId(String socialId);

    @Query("select u from UserEntity u left join fetch u.family where u.family.id = (select u2.family.id from UserEntity u2 where u2.id=:userId) and u.isLeader is true")
    Optional<UserEntity> findLeader(@Param("userId") Long userId);

    @Query("select u.family from UserEntity u where u.id = :id")
    Optional<FamilyEntity> getFamily(@Param("id") Long userId);

}
