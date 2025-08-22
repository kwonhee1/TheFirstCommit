package TheFirstCommit.demo.feed.repository;

import TheFirstCommit.demo.feed.entity.ImgFeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgFeedRepository extends JpaRepository<ImgFeedEntity, Long> {
    List<ImgFeedEntity> findAllByFeedIdAndImgIdIn(Long feedId, List<Long> imgIds);
}