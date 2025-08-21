package TheFirstCommit.demo.news.dto;

import TheFirstCommit.demo.news.entity.NewsEntity;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class ResponseNewsDto {
    private final Long newsId;
    private final LocalDate publishedAt;
    private final String cid;

    public ResponseNewsDto(NewsEntity news) {
        this.newsId = news.getId();
        this.publishedAt = news.getPublishedAt();
        this.cid = news.getImg().getCid();
    }
}