package TheFirstCommit.demo.news.dto;

import TheFirstCommit.demo.news.entity.NewsEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NewsListResponseDto {
    private final Long newsId;
    private final LocalDate publishedAt;
    private final String cid; // 필드 이름을 pdfCid에서 cid로 변경하는 것이 더 명확

    public NewsListResponseDto(NewsEntity news) {
        this.newsId = news.getId();
        this.publishedAt = news.getPublishedAt();
        this.cid = news.getImg().getCid();
    }
}