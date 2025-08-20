package TheFirstCommit.demo.payment.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.payment.dto.CardInfoDto;
import TheFirstCommit.demo.payment.dto.RequestSaveCardDto;
import TheFirstCommit.demo.payment.dto.response.ResponseCardInfoDto;
import TheFirstCommit.demo.payment.entity.CardEntity;
import TheFirstCommit.demo.payment.repository.CardRepository;
import TheFirstCommit.demo.payment.repository.PaymentRepository;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserValidateService;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final UserValidateService userValidateService;
    @Value("${toss.secret}")
    private String TOSS_SECRET;

    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public CardInfoDto saveCard(UserEntity user, RequestSaveCardDto dto) {
        Optional<CardEntity> card = getCardOpt(user);
        if(card.isPresent())
            remove(card.get());

        CardInfoDto cardInfo = getCardInfo(dto);
        cardRepository.save(
            CardEntity.builder()
                .customerKey(cardInfo.getCustomerKey())
                .billingKey(cardInfo.getBillingKey())
                .cardCompany(cardInfo.getCardCompany())
                .cardNumber(cardInfo.getCardNumber())
                .user(user)
                .build()
        );

        userValidateService.getFamily(user).setIsChanged(false);

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
