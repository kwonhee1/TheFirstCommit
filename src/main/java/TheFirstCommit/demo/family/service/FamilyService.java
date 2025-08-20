package TheFirstCommit.demo.family.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.FamilyCodeUtil;
import TheFirstCommit.demo.family.dto.FamilyMemberDto;
import TheFirstCommit.demo.family.dto.request.RequestElderDto;
import TheFirstCommit.demo.family.dto.request.RequestJoinFamilyDto;
import TheFirstCommit.demo.family.dto.request.RequestNewFamilyDto;
import TheFirstCommit.demo.family.dto.response.FamilyPageDto;
import TheFirstCommit.demo.family.dto.response.HomePageDto;
import TheFirstCommit.demo.family.dto.response.ResponseElderDto;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyDto;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyMemberDto;
import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.repository.ElderRepository;
import TheFirstCommit.demo.family.repository.FamilyRepository;
import TheFirstCommit.demo.feed.dto.ResponseFeedDto;
import TheFirstCommit.demo.feed.service.FeedService;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.payment.dto.response.ResponsePaymentSummeryDto;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.dto.request.UpdateUserFamilyDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import TheFirstCommit.demo.user.service.UserValidateService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final ElderRepository elderRepository;
    private final UserService userService;
    private final ImgService imgService;
    private final UserValidateService userValidateService;
    private final PaymentService paymentService;
    private final FeedService feedService;

    public void save(UserEntity user, RequestNewFamilyDto familyDto, RequestElderDto elderDto, MultipartFile elderImgFile) {
        if(user.getFamily() != null)
            throw new CustomException(ErrorCode.ALREADY_EXIST, "family");

        // family 저장, elder 저장, user family update
        ImgEntity imgEntity = imgService.save(elderImgFile);
        FamilyEntity family = familyRepository.save(familyDto.toFamilyEntity());
        elderRepository.save(elderDto.toEntity(family, imgEntity));
        userService.updateUserFamily(user, new UpdateUserFamilyDto(family, familyDto.getRelation(), true));
    }

    // elder 정보 수정 (leader 만 가능!)
    public void updateFamily(UserEntity user, RequestElderDto dto, MultipartFile elderImgFile) {
        if(!user.isLeader())
            throw new CustomException(ErrorCode.NOT_LEADER);
        ElderEntity elder = elderRepository.findByUserId(user.getId()).orElseThrow(
            ()->{return new CustomException(ErrorCode.NOT_FOUND, "elder");} // api인데 family가 없거나 elder가 없는 경우는 불가능함
        );

        elder.update(dto);
        if(elderImgFile != null) {
            imgService.update(elder.getImg(), elderImgFile);
        }
        elderRepository.save(elder);
    }

    public String getFamilyCode(UserEntity user) {
        if(user.getFamily() == null)
            throw new CustomException(ErrorCode.NOT_FOUND, "family");
        return FamilyCodeUtil.encode(user.getFamily().getId());
    }

    // join with family code
    public void joinFamily(UserEntity user, RequestJoinFamilyDto dto) {
        FamilyEntity family = getFamilyByCode(dto.getFamilyCode()).orElseThrow(()->{return new CustomException(ErrorCode.NOT_FOUND, "family");});
        userService.updateUserFamily(user, new UpdateUserFamilyDto(family, dto.getRelation(), false));
    }

    ///  get dtos

    public FamilyPageDto getFamilyPage(UserEntity user) {
        FamilyEntity family = userValidateService.getFamily(user);

        ResponseElderDto elderDto = ResponseElderDto.of(family.getElder());
        ResponseFamilyMemberDto familyMemberDto = ResponseFamilyMemberDto.of(getFamilyMember(family));

        return FamilyPageDto.builder().elder(elderDto).member(familyMemberDto).build();
    }

    public HomePageDto getHomePageDto(UserEntity user) {
        ResponsePaymentSummeryDto payment = paymentService.getFamilyPaymentSummeryDto(user);
        ResponseFamilyDto familyDto = getFamilyDto(user);
        List<ResponseFeedDto> feedList = null; // 추가 feed 구현 이후 구현

        return new HomePageDto(payment, familyDto, feedList);
    }

    public ResponseFamilyDto getFamilyDto(UserEntity user) {
        return ResponseFamilyDto.of(user.getFamily().getFamilyName(), familyRepository.getMemberCount(user.getFamily().getId()));
    }

    private FamilyMemberDto getFamilyMember(FamilyEntity family) {
        return FamilyMemberDto.of(family.getFamilyName(), familyRepository.getFamilyMember(family.getId()), familyRepository.getFamilyLeader(family.getId()));
    }

    // --------- validate

    public Optional<FamilyEntity> getFamilyByCode(String familyCode) {
        if(familyCode == null)
            throw new CustomException(ErrorCode.NOT_FOUND, "familyCode");
        return familyRepository.findById(FamilyCodeUtil.decode(familyCode));
    }

}
