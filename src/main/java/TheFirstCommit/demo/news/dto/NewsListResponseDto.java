package TheFirstCommit.demo.news.dto;

import TheFirstCommit.demo.news.entity.NewsEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NewsListResponseDto {
    private Long newsId;
    private LocalDate publishedAt;
    private String firstPageCid; // 목록에 보여줄 대표 이미지 (1페이지 이미지의 CID)

    public NewsListResponseDto(NewsEntity news) {
        this.newsId = news.getId();
        this.publishedAt = news.getPublishedAt();
        // 소식지에 이미지가 하나 이상 있는 경우, 첫 페이지 이미지의 CID를 가져옴
        if (news.getNewsImgs() != null && !news.getNewsImgs().isEmpty()) {
            this.firstPageCid = news.getNewsImgs().get(0).getImg().getCid();
        } else {
            this.firstPageCid = null; // 이미지가 없는 경우 null
        }
    }
}