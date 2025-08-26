package TheFirstCommit.demo.payment.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.payment.dto.CardInfoDto;
import TheFirstCommit.demo.payment.dto.RequestSaveCardDto;
import TheFirstCommit.demo.payment.dto.response.ResponseCardInfoDto;
import TheFirstCommit.demo.payment.dto.response.ResponseNoCardDto;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentInfoDto;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentSummeryDto;
import TheFirstCommit.demo.payment.entity.CardEntity;
import TheFirstCommit.demo.payment.entity.PaymentEntity;
import TheFirstCommit.demo.payment.repository.CardRepository;
import TheFirstCommit.demo.payment.repository.PaymentRepository;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.repository.UserRepository;
import TheFirstCommit.demo.user.service.UserValidateService;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
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

    @Value("${toss.secret}")
    private String TOSS_SECRET;
    @Value("${payment.price}")
    private Long MONTHLY_PAYMENT_PRICE;

    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserValidateService userValidateService;

    // payment
    @Scheduled(cron = "0 0 9 * * *")
    public void MonthlyPayment() {
        List<UserEntity> allLeader = paymentRepository.findAllLeader();

        for(UserEntity leader : allLeader) {
            FamilyEntity family = leader.getFamily();
            Optional<CardEntity> card = getCardOpt(leader);

            // 결제 일인지 확인 먼저
            if(family.getThisMonthPaymentDate().equals(LocalDate.now()) && !isPayed(family)) {
                if(card.isPresent())
                    payment(card.get(), family);
                else
                    log.info("monthly payment fail (no card)" + family.getId());
            }
        }
    }

    public void payment(CardEntity card, FamilyEntity family) {
        if(tossPayment(card)) {
            log.info("monthly payment success " + family.getId());
            paymentRepository.save(
                PaymentEntity.builder()
                    .card(card)
                    .family(family)
                    .build()
            );
        }else{
            log.info("monthly payment fail (fail toss)" + family.getId());
        }
    }

    private boolean tossPayment(CardEntity card) {
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
        return tossPayment(getCard(user));
    }

    @Transactional
    public Object getFamilyPaymentDto(UserEntity user) {
        UserEntity leader = userValidateService.findLeader(user);
        FamilyEntity family = leader.getFamily();

        if(isPayed(family))
            return ResponsePaymentInfoDto.of(leader, family);
        else
            return new ResponseNoCardDto(MONTHLY_PAYMENT_PRICE);
    }

    public ResponsePaymentSummeryDto getFamilyPaymentSummeryDto(UserEntity user) {
        UserEntity leader = userValidateService.findLeader(user);
        return ResponsePaymentSummeryDto.of(isPayed(leader.getFamily()), leader.getFamily().getPaymentDay());
    }

    private boolean isPayed(FamilyEntity family) {
        LocalDate paymentDay = family.getPaymentDay().nextFeedDay(); // 뒤로 30일 이전
        LocalDate startDate = paymentDay.minusDays(30);
        return paymentRepository.isPaymentByFamily(family.getId(), startDate.atTime(0,1), paymentDay.atTime(23,59)).isPresent();
    }

    ///  card


    @Transactional
    public CardInfoDto saveCard(UserEntity user, RequestSaveCardDto dto) {
        Optional<CardEntity> oldCardOpt = getCardOpt(user);
        if(oldCardOpt.isPresent()) {
            remove(oldCardOpt.get());
        }

        CardInfoDto cardInfo = getCardInfo(dto);
        CardEntity newCard = cardRepository.save(
            CardEntity.builder()
                .customerKey(cardInfo.getCustomerKey())
                .billingKey(cardInfo.getBillingKey())
                .cardCompany(cardInfo.getCardCompany())
                .cardNumber(cardInfo.getCardNumber())
                .user(user)
                .build()
        );

        Optional<FamilyEntity> familyOpt = userRepository.getFamily(user.getId());
        if(familyOpt.isPresent()) {
            familyOpt.get().setIsChanged(false);
            // 카드를 가족 생성 후 / 처음으로 등록 하는 경우
            if(familyOpt.get().getSincePayDay() == null) {
                familyOpt.get().payNow();
                payment(newCard, familyOpt.get());
            }
            if(familyOpt.get().getPaymentDay() == null) 
                familyOpt.get().updatePaymentDay(dto.getPaymentDay());
        }

        log.info("Card saved " + user.getId() + ", " + user.getName());
        return cardInfo;
    }

    @Transactional
    public void remove(UserEntity user) {
        remove(getCard(user));
    }

    private void remove(CardEntity card) {
        // payment 값이 있으면 paymentEntity : cardId null 값 update
        paymentRepository.updateCardNull(card.getId());
        cardRepository.deleteById(card.getId());
    }

    // 카드 정보
    public ResponseCardInfoDto getCardInfo(UserEntity user) {
        return ResponseCardInfoDto.of(getCard(user));
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

    private CardEntity getPaymentCard(UserEntity user) {
        List<CardEntity> cards = cardRepository.findAllByUserId(user.getId());
        if(cards.isEmpty() || cards.size() == 0)
            throw new CustomException(ErrorCode.NOT_EXIST_CARD);
        if(cards.size() >= 1)
            throw new CustomException(ErrorCode.TOO_MANY_CARDS);

        return cards.stream().findFirst().get();
    }

    public Optional<CardEntity> getCardOpt(UserEntity user) {
        return cardRepository.findAllByUserId(user.getId()).stream().findFirst();
    }

    protected CardEntity getCard(UserEntity user) {
        Optional<CardEntity> card = getCardOpt(user);
        if(card.isEmpty())
            throw new CustomException(ErrorCode.NOT_EXIST_CARD);
        return card.get();
    }
}
