package TheFirstCommit.demo.feed.repository;

import TheFirstCommit.demo.feed.entity.FeedEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

    @Query("select count(*) from FeedEntity f where f.family.id = :id")
    Long countByFamily(@Param("id") Long familyId);

    @Query("select f from FeedEntity f where f.family.id = :familyId order by f.createdAt desc")
    List<FeedEntity> findByFamily(@Param("familyId") Long familyId);

}
