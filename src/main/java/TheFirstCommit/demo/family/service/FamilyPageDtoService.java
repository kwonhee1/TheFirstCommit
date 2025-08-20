package TheFirstCommit.demo.family.service;

import TheFirstCommit.demo.family.dto.page.FamilyPageDto;
import TheFirstCommit.demo.family.dto.page.HomePageDto;
import TheFirstCommit.demo.family.dto.response.ResponseElderDto;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyDto;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyMemberDto;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.feed.dto.ResponseFeedDto;
import TheFirstCommit.demo.feed.service.FeedService;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentSummeryDto;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserValidateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyPageDtoService {

    private final UserValidateService userValidateService;
    private final PaymentService paymentService;
    private final FamilyService familyService;
    private final FeedService feedService;

    public FamilyPageDto getFamilyPage(UserEntity user) {
        FamilyEntity family = userValidateService.getFamily(user);

        ResponseElderDto elderDto = ResponseElderDto.of(family.getElder());
        ResponseFamilyMemberDto familyMemberDto = ResponseFamilyMemberDto.of(familyService.getFamilyMember(family));

        return FamilyPageDto.builder().elder(elderDto).member(familyMemberDto).build();
    }

    public HomePageDto getHomePageDto(UserEntity user) {
        ResponsePaymentSummeryDto payment = paymentService.getFamilyPaymentSummeryDto(user);
        ResponseFamilyDto familyDto = familyService.getFamilyDto(user);
        List<ResponseFeedDto> feedList = feedService.getFeedDtoList(userValidateService.getFamily(user));

        return new HomePageDto(payment, familyDto, feedList);
    }

}
