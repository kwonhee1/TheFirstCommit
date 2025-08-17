package TheFirstCommit.demo.family.repository;

import TheFirstCommit.demo.family.entity.ElderEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ElderRepository extends JpaRepository<ElderEntity, Long> {
    @Query("select u.family.elder from UserEntity u where u.id = :userId")
    Optional<ElderEntity> findByUserId(@Param("userId") Long userId);
}
