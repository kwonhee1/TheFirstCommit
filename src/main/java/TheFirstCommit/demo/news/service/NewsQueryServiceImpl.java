package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.news.dto.NewsListResponseDto;
import TheFirstCommit.demo.news.dto.ResponseNewsBoxDto;
import TheFirstCommit.demo.news.entity.DeliveryStatus;
import TheFirstCommit.demo.news.entity.NewsEntity;
import TheFirstCommit.demo.news.repository.NewsRepository;
import java.time.LocalDateTime;
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
        List<NewsEntity> allNews = newsRepository.findByFamilyWithImgOrderByPublishedAtDesc(family.getId());

        LocalDateTime startDay = family.getPaymentDay().beforeFeedDay().atTime(0,0);
        LocalDateTime endDay = family.getPaymentDay().nextFeedDay().atTime(23,59);

        Optional<NewsEntity> currentMonthNews = newsRepository.findThisMonthByFamily(family.getId(), startDay, endDay);

        List<NewsListResponseDto> allNewsDto = allNews.stream()
                .map(NewsListResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseNewsBoxDto(allNewsDto, currentMonthNews.isPresent()?currentMonthNews.get().getDeliveryStatus() : DeliveryStatus.COMPLETED_PRODUCTION);
    }

}