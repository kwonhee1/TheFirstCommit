package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.news.dto.NewsListResponseDto;
import TheFirstCommit.demo.news.dto.ResponseNewsBoxDto;
import TheFirstCommit.demo.news.entity.DeliveryStatus;
import TheFirstCommit.demo.news.entity.NewsEntity;
import TheFirstCommit.demo.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsQueryServiceImpl implements NewsQueryService {

    private final NewsRepository newsRepository;

    @Override
    public ResponseNewsBoxDto getNewsBox(FamilyEntity family) {
        // 1. 해당 가족의 모든 소식지를 최신순으로 조회
        List<NewsEntity> allNews = newsRepository.findByFamilyWithImgOrderByPublishedAtDesc(family);

        // 2. '이번 달 소식지'를 찾기 위한 기준일 계산
        LocalDate startDay = ;
        LocalDate endDay = ;

        // 3. '이번 달 소식지' 찾기
        Optional<NewsEntity> currentMonthNews = ;

        // 4. 전체 소식지 목록을 DTO로 변환
        List<NewsListResponseDto> allNewsDto = allNews.stream()
                .map(NewsListResponseDto::new)
                .collect(Collectors.toList());

        // 6. 최종 DTO 빌드 후 반환
        return ResponseNewsBoxDto.builder()
                .news(allNewsDto) // 전체 소식지 목록
                .currentMOnthNews(currentMonthNews)
                .build();
    }

}