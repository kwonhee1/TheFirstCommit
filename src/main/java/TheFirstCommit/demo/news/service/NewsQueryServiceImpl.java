package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.news.dto.NewsListResponseDto;
import TheFirstCommit.demo.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsQueryServiceImpl implements NewsQueryService {

    private final NewsRepository newsRepository;

    @Override
    public List<NewsListResponseDto> findAllNews(FamilyEntity family) {
        // 새로 만든 Repository 메소드를 호출하도록 수정
        return newsRepository.findByFamilyWithImgOrderByPublishedAtDesc(family).stream()
                .map(NewsListResponseDto::new)
                .collect(Collectors.toList());
    }
}