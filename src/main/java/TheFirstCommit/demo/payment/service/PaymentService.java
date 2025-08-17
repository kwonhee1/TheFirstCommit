package TheFirstCommit.demo.payment.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.service.FamilyService;
import TheFirstCommit.demo.payment.dto.CardInfoDto;
import TheFirstCommit.demo.payment.dto.RequestSaveCardDto;
import TheFirstCommit.demo.payment.entity.CardEntity;
import TheFirstCommit.demo.payment.entity.PaymentEntity;
import TheFirstCommit.demo.payment.repository.CardRepository;
import TheFirstCommit.demo.payment.repository.PaymentRepository;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    @Value("${toss.secret}")
    private String TOSS_SECRET;
    @Value("${payment.price}")
    private Long MONTHLY_PAYMENT_PRICE;

    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;
    private final FamilyService familyService;

    // card

    public CardInfoDto saveCard(UserEntity user, RequestSaveCardDto dto) {
        CardInfoDto cardInfo = getCardInfo(dto);
        cardRepository.save(
            CardEntity.builder()
                .customerKey(cardInfo.getCustomerKey())
                .billingKey(cardInfo.getBillingKey())
                .user(user)
                .build()
        );
        log.info("Card saved " + user.getId() + ", " + user.getName());
        return cardInfo;
    }

    public void remove(UserEntity user) {
        // not yet
    }

    private CardInfoDto getCardInfo(RequestSaveCardDto dto) {
        log.info(dto.toString());
        String auth = "Basic " + Base64.getEncoder().encodeToString( (TOSS_SECRET+":") .getBytes());

        WebClient webClient = WebClient.builder()
            .baseUrl("https://api.tosspayments.com")
            .defaultHeader("Authorization", auth)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();

        Map<String, Object> response = webClient.post()
            .uri("/v1/billing/authorizations/issue")
            .bodyValue(dto)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
            clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    log.error("Toss API Error Body: {}", errorBody);
                    return Mono.error(new CustomException(ErrorCode.FAIL_SAVE_CARD));
                })
            )
            .bodyToMono(Map.class)
            .block(); // 동기 호출

        return new CardInfoDto(response);
    }

    // payment
    @Scheduled(cron = "${payment.four-week-sunday}")
    @Scheduled(cron = "${payment.second-week-sunday}")
    public void MonthlyPayment() {
        List<UserEntity> allLeader = paymentRepository.findAllLeader();
        for(UserEntity leader : allLeader) {
            CardEntity card = leader.getCard();
            FamilyEntity family = leader.getFamily();

            if(payment(card)) {
                log.info("monthly payment success " + family.getId() + ", "); // 이후 받는 분 정보 추가 log 출력
                paymentRepository.save(
                    PaymentEntity.builder()
                        .card(card)
                        .family(family)
                        .build()
                );
            }
            else
                log.info("monthly payment fail " + family.getId() + ", ");
        }
    }

    public boolean payment(CardEntity card) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("customerKey", card.getCustomerKey());
        requestBody.put("amount", MONTHLY_PAYMENT_PRICE);
        requestBody.put("orderId", UUID.randomUUID().toString());
        requestBody.put("orderName", "deardread 정기 구독");
        requestBody.put("customerEmail", "고객 이메일");
        requestBody.put("customerName", "고객 이름");
        requestBody.put("taxFreeAmount", 0);

        String auth = "Basic " + Base64.getEncoder().encodeToString( (TOSS_SECRET+":") .getBytes(StandardCharsets.UTF_8));

        WebClient webClient = WebClient.builder()
            .baseUrl("https://api.tosspayments.com/v1/billing")
            .defaultHeader("Authorization", auth)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();

        return webClient.post()
            .uri("/{billingKey}", card.getBillingKey())
            .bodyValue(requestBody)
            .retrieve()
            .onStatus(
                httpStatusCode -> httpStatusCode.is4xxClientError(),
                clientResponse -> {
                    log.info("Toss monthly payment error: status={}, body={}",
                        clientResponse.statusCode(),
                        clientResponse.bodyToMono(String.class).block());
                    return Mono.empty();
                }
            )
            .bodyToMono(String.class)
            .map(body -> true) // 정상 응답이면 true
            .defaultIfEmpty(false) // onStatus에서 Mono.empty()를 반환하면 여기서 false 처리
            .block();
    }
}
