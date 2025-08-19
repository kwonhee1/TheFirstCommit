package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.news.dto.NewsDetailResponseDto;
import TheFirstCommit.demo.news.dto.NewsListResponseDto;

import java.util.List;

public interface NewsQueryService {
    List<NewsListResponseDto> findAllNews();
    NewsDetailResponseDto findNewsById(Long newsId);
}