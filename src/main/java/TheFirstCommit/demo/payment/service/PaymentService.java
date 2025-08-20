package TheFirstCommit.demo.payment.service;

import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.service.FamilyService;
import TheFirstCommit.demo.payment.dto.response.ResponseNoCardDto;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentInfoDto;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentSummeryDto;
import TheFirstCommit.demo.payment.entity.CardEntity;
import TheFirstCommit.demo.payment.entity.PaymentEntity;
import TheFirstCommit.demo.payment.repository.PaymentRepository;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserValidateService;
import TheFirstCommit.demo.user.service.UserValidateServiceImpl;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final UserValidateService userValidateService;
    @Value("${toss.secret}")
    private String TOSS_SECRET;
    @Value("${payment.price}")
    private Long MONTHLY_PAYMENT_PRICE;

    private final PaymentRepository paymentRepository;
    private final CardService cardService;

    // payment
    @Scheduled(cron = "${payment.four-week-sunday}")
    @Scheduled(cron = "${payment.second-week-sunday}")
    public void MonthlyPayment() {
        List<UserEntity> allLeader = paymentRepository.findAllLeader();
        for(UserEntity leader : allLeader) {
            FamilyEntity family = leader.getFamily();
            Optional<CardEntity> card = cardService.getCardOpt(leader);

            if(card.isPresent() && payment(card.get())) {
                log.info("monthly payment success " + family.getId() + ", "); // 이후 받는 분 정보 추가 log 출력
                paymentRepository.save(
                    PaymentEntity.builder()
                        .card(card.get())
                        .family(family)
                        .build()
                );
            }
            else
                log.info("monthly payment fail " + family.getId() + ", ");
        }
    }

    private boolean payment(CardEntity card) {
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

    public boolean testPayment(UserEntity user) {
        return payment(cardService.getCard(user));
    }

    @Transactional
    public Object getFamilyPaymentDto(UserEntity user) {
        UserEntity leader = userValidateService.findLeader(user);
        Optional<CardEntity> cardOpt = cardService.getCardOpt(leader);
        PaymentDay paymentDay = leader.getFamily().getPaymentDay();

        if(cardOpt.isPresent())
            return ResponsePaymentInfoDto.of(paymentDay, leader);
        else
            return new ResponseNoCardDto(MONTHLY_PAYMENT_PRICE);
    }

    public ResponsePaymentSummeryDto getFamilyPaymentSummeryDto(UserEntity user) {
        UserEntity leader = userValidateService.findLeader(user);
        return ResponsePaymentSummeryDto.of(!leader.getCard().isEmpty(), leader.getFamily().getPaymentDay());
    }
}
