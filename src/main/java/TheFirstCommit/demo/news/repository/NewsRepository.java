package TheFirstCommit.demo.news.repository;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.news.entity.NewsEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Query("SELECT n FROM NewsEntity n left JOIN FETCH n.img WHERE n.family.id = :familyId ORDER BY n.createdAt DESC")
    List<NewsEntity> findByFamilyWithImgOrderByPublishedAtDesc(@Param("familyId") Long familyId);

    @Query("select n "
        + "from NewsEntity n left join fetch n.img "
        + "where n.family.id = :familyId and n.createdAt between :start and :end")
    Optional<NewsEntity> findThisMonthByFamily(
        @Param("familyId") Long familyId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}