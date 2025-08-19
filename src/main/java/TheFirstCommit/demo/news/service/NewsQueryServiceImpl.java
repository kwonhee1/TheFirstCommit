package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.news.dto.NewsDetailResponseDto;
import TheFirstCommit.demo.news.dto.NewsListResponseDto;
import TheFirstCommit.demo.news.entity.NewsEntity;
import TheFirstCommit.demo.news.repository.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회만 하므로 readOnly = true 설정
public class NewsQueryServiceImpl implements NewsQueryService {

    private final NewsRepository newsRepository;

    @Override
    public List<NewsListResponseDto> findAllNews() {
        // 모든 소식지를 가져와서 DTO 리스트로 변환하여 반환
        return newsRepository.findAllByOrderByPublishedAtDesc().stream() // 최신순으로 정렬
                .map(NewsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDetailResponseDto findNewsById(Long newsId) {
        // ID로 소식지를 찾고, 없으면 예외 발생
        NewsEntity news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 소식지를 찾을 수 없습니다: " + newsId));

        // 찾은 소식지를 상세 DTO로 변환하여 반환
        return new NewsDetailResponseDto(news);
    }
}