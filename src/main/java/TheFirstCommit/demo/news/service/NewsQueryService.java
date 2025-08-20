package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.news.dto.NewsListResponseDto;

import java.util.List;

public interface NewsQueryService {
    // 파라미터로 FamilyEntity를 받도록 수정
    List<NewsListResponseDto> findAllNews(FamilyEntity family);
}