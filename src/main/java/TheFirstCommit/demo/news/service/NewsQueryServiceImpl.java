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
        LocalDate cutoffDate = getCutoffDateForCurrentMonth(family.getPaymentDay());

        // 3. '이번 달 소식지' 찾기
        Optional<NewsEntity> currentMonthNews = allNews.stream()
                .filter(news -> !news.getPublishedAt().isBefore(cutoffDate))
                .findFirst();

        // 4. 전체 소식지 목록을 DTO로 변환
        List<NewsListResponseDto> allNewsDto = allNews.stream()
                .map(NewsListResponseDto::new)
                .collect(Collectors.toList());

        // 5. 이번 달 배송 상태 결정 (없으면 '제작 완료'를 기본값으로)
        String deliveryStatus = currentMonthNews
                .map(news -> news.getDeliveryStatus().getDescription())
                .orElse(DeliveryStatus.COMPLETED_PRODUCTION.getDescription());

        // 6. 최종 DTO 빌드 후 반환
        return ResponseNewsBoxDto.builder()
                .news(allNewsDto) // 전체 소식지 목록
                .currentMonthDeliveryStatus(deliveryStatus)
                .build();
    }

    /**
     * 이번 달의 N번째 주 일요일 날짜를 계산하는 헬퍼 메소드
     */
    private LocalDate getCutoffDateForCurrentMonth(PaymentDay paymentDay) {
        LocalDate today = LocalDate.now();
        if (paymentDay == null) {
            // 가족에게 paymentDay가 설정되지 않은 경우를 대비한 기본값
            paymentDay = PaymentDay.SECOND_SUNDAY;
        }
        int weekOrdinal = (paymentDay == PaymentDay.SECOND_SUNDAY) ? 2 : 4;
        return today.with(TemporalAdjusters.dayOfWeekInMonth(weekOrdinal, DayOfWeek.SUNDAY));
    }
}