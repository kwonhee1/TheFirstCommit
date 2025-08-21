package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.news.dto.ResponseNewsBoxDto;
import TheFirstCommit.demo.news.dto.ResponseNewsDto;
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

        // 2. '이번 달'의 시작 날짜를 기준으로 설정 (예: 8월 1일)
        LocalDate startOfCurrentMonth = LocalDate.now().withDayOfMonth(1);

        // 3. '이번 달 소식지' 찾기 (이번 달에 발행된 가장 최신 소식지)
        Optional<NewsEntity> currentMonthNews = allNews.stream()
                .filter(news -> !news.getPublishedAt().isBefore(startOfCurrentMonth))
                .findFirst(); // 이미 최신순 정렬이라 첫 번째 항목이 가장 최신

        // 4. '과거 소식지' 목록 만들기 (이번 달 이전에 발행된 모든 소식지)
        List<ResponseNewsDto> pastNews = allNews.stream()
                .filter(news -> news.getPublishedAt().isBefore(startOfCurrentMonth))
                .map(ResponseNewsDto::new)
                .collect(Collectors.toList());

        // 5. 이번 달 소식지 배송 상태 결정
        String deliveryStatus = currentMonthNews
                .map(news -> news.getDeliveryStatus().getDescription()) // 있으면 해당 상태 반환
                .orElse("발행된 소식지가 없습니다"); // 없으면 미발행 상태 반환

        // 6. 최종 DTO 빌드 후 반환
        return ResponseNewsBoxDto.builder()
                .news(pastNews)
                .currentMonthDeliveryStatus(deliveryStatus)
                .build();
    }

    private LocalDate getCutoffDateForCurrentMonth(PaymentDay paymentDay) {
        LocalDate today = LocalDate.now();
        if (paymentDay == null) {
            paymentDay = PaymentDay.SECOND_SUNDAY;
        }
        int weekOrdinal = (paymentDay == PaymentDay.SECOND_SUNDAY) ? 2 : 4;
        return today.with(TemporalAdjusters.dayOfWeekInMonth(weekOrdinal, DayOfWeek.SUNDAY));
    }
}