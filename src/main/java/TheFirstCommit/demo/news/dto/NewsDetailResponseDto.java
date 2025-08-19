package TheFirstCommit.demo.news.dto;

import TheFirstCommit.demo.news.entity.NewsEntity;
import TheFirstCommit.demo.news.entity.NewsImgEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NewsDetailResponseDto {
    private final Long newsId;
    private final String pdfCid; // PDF 다운로드용
    private final List<PageImageDto> pages; // 슬라이드 뷰를 위한 페이지 이미지 목록

    public NewsDetailResponseDto(NewsEntity news) {
        this.newsId = news.getId();
        this.pdfCid = news.getPdfCid();
        this.pages = news.getNewsImgs().stream()
                .map(PageImageDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    private static class PageImageDto {
        private final String cid;
        private final int pageNumber;

        public PageImageDto(NewsImgEntity newsImg) {
            this.cid = newsImg.getImg().getCid();
            this.pageNumber = newsImg.getPageNumber();
        }
    }
}