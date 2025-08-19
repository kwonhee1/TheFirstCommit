package TheFirstCommit.demo.news.repository;

import TheFirstCommit.demo.news.entity.NewsImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsImgRepository extends JpaRepository<NewsImgEntity, Long> {}