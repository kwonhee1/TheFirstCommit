package TheFirstCommit.demo.news.dto;

import TheFirstCommit.demo.news.entity.NewsEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NewsListResponseDto {
    private final Long newsId;
    private final LocalDate publishedAt;
    private final String cid;
    private final String deliveryStatus;

    public NewsListResponseDto(NewsEntity news) {
        this.newsId = news.getId();
        this.publishedAt = news.getPublishedAt();
        this.cid = news.getImg().getCid();
        this.deliveryStatus = news.getDeliveryStatus().getDescription();
    }
}