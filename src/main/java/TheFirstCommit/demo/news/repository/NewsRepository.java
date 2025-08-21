package TheFirstCommit.demo.news.repository;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.news.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Query("SELECT n FROM NewsEntity n JOIN FETCH n.img WHERE n.family = :family ORDER BY n.publishedAt DESC")
    List<NewsEntity> findByFamilyWithImgOrderByPublishedAtDesc(@Param("family") FamilyEntity family);
}