package TheFirstCommit.demo.imgFeed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgFeedRepository extends JpaRepository<ImgFeedEntity, Long> {}