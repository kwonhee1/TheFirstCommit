package TheFirstCommit.demo.news.dto;

import TheFirstCommit.demo.news.entity.NewsEntity;
import TheFirstCommit.demo.img.ImgDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class NewsListResponseDto {
    private final Long newsId;
    private final String publishedAt;
    private final ImgDto file;
    private final String deliveryStatus;

    public NewsListResponseDto(NewsEntity news) {
        this.newsId = news.getId();
        this.publishedAt = dateToStr(news.getCreatedAt());
        this.file = ImgDto.of(news.getImg());
        this.deliveryStatus = news.getDeliveryStatus().description;
    }

    private String dateToStr(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 MM월 '소식지'");
        return date.format(formatter);
    }
}