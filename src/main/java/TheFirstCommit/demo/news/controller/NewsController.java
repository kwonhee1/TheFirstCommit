package TheFirstCommit.demo.news.controller;

import TheFirstCommit.demo.news.dto.NewsDetailResponseDto; // 상세 조회용 DTO
import TheFirstCommit.demo.news.dto.NewsListResponseDto;   // 목록 조회용 DTO
import TheFirstCommit.demo.news.service.NewsQueryService; // 조회 전용 서비스 (생성 서비스와 분리하는 것이 좋음)
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news") // <-- 사용자를 위한 /api/news 경로
@RequiredArgsConstructor
public class NewsController {

    // CQS 패턴에 따라 조회를 위한 서비스는 별도로 두는 것이 좋습니다.
    private final NewsQueryService newsQueryService;

    /**
     * 소식지 목록을 조회하는 API
     */
    @GetMapping
    public ResponseEntity<List<NewsListResponseDto>> getNewsList() {
        List<NewsListResponseDto> newsList = newsQueryService.findAllNews();
        return ResponseEntity.ok(newsList);
    }

    /**
     * 특정 소식지의 상세 내용을 조회하는 API
     * @param newsId 조회할 소식지의 ID
     */
    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDetailResponseDto> getNewsDetail(@PathVariable Long newsId) {
        NewsDetailResponseDto newsDetail = newsQueryService.findNewsById(newsId);
        return ResponseEntity.ok(newsDetail);
    }
}